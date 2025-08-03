package com.starter.starter_pack.common.store

import com.starter.starter_pack.common.store.cache.InMemoryCache
import com.starter.starter_pack.common.store.cache.StoreCache
import com.starter.starter_pack.common.store.network.StoreNetwork
import com.starter.starter_pack.common.store.network.StoreUpdater
import com.starter.starter_pack.common.result.catching
import com.starter.starter_pack.common.result.onSuccess
import com.starter.starter_pack.common.result.Result

open class MutableStore<KEY, DATA, UPDATE>(
    network: StoreNetwork<KEY, DATA>,
    private val networkErrorConverter: Throwable.() -> Throwable = { this },
    private val cache: StoreCache<KEY, DATA> = InMemoryCache(),
    private val updater: StoreUpdater<KEY, UPDATE, DATA>,
) : Store<KEY, DATA>(network, networkErrorConverter, cache) {

    suspend fun update(key: KEY, update: UPDATE): Result<DATA> {
        return catching(networkErrorConverter) { updater(key, update) }.onSuccess {
            cache.set(
                key,
                it
            )
        }
    }
}
