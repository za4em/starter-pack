package com.starter.starter_pack.common.store

import com.starter.starter_pack.common.store.cache.InMemoryCache
import com.starter.starter_pack.common.store.cache.StoreCache
import com.starter.starter_pack.common.store.network.StoreNetwork
import com.starter.starter_pack.common.result.catching
import com.starter.starter_pack.common.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class Store<KEY : Any?, DATA>(
    private val network: StoreNetwork<KEY, DATA>,
    private val networkErrorConverter: (Throwable) -> Throwable = { it },
    private val cache: StoreCache<KEY, DATA> = InMemoryCache(),
) {

    private val mutex = Mutex()

    /*
     * Get value
     * */

    suspend fun getFresh(key: KEY): Result<DATA> {
        return mutex.withLock {
            catching(converter = networkErrorConverter) {
                val data = network(key)
                cache.set(key, data)
                data
            }
        }
    }

    fun getCache(key: KEY): DATA? {
        return cache.get(key)
    }

    suspend fun get(key: KEY): Result<DATA> {
        return getCache(key)?.let { Result.success(it) } ?: getFresh(key)
    }

    /*
     * Flow
     * */

    fun observeCacheNotNull(key: KEY): Flow<DATA> {
        return cache.observe(key).mapNotNull { it }.distinctUntilChanged()
    }

    fun observeCache(key: KEY): Flow<DATA?> {
        return cache.observe(key).distinctUntilChanged()
    }

    fun observeFreshAndCache(key: KEY): Flow<Result<DATA>> {
        return flow {

            /*
             * Trying emit cache
             * */
            val cache = getCache(key)
            cache?.let { emit(Result.success(it)) }

            /*
             * Trying emit fresh
             * */
            val fresh = getFresh(key)
            if (fresh.data != null) emit(fresh)
            else if (fresh.error != null) emit(Result.error(fresh.error!!, data = cache))

            /*
             * All new emits from cache are fresh
             * */
            emitAll(observeCacheNotNull(key).drop(1).map { Result.success(it) })
        }
            .distinctUntilChanged()
    }

    suspend fun clear() {
        cache.clearAll()
    }

    suspend fun clear(key: KEY) {
        cache.clear(key)
    }
}
