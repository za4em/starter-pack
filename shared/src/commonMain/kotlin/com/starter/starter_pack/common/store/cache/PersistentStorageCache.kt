package com.starter.starter_pack.common.store.cache

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

open class PersistentStorageCache<PARAMS, DATA : @Serializable Any>(
    private val store: KStore<DATA>,
) : StoreCache<PARAMS, DATA> {

    override fun observe(params: PARAMS): Flow<DATA?> {
        return store.updates
    }

    override suspend fun get(params: PARAMS): DATA? {
        return store.get()
    }

    override suspend fun set(params: PARAMS, data: DATA) {
        store.update { data }
    }

    override suspend fun clear(params: PARAMS) {
        store.delete()
    }

    override suspend fun clearAll() {
        store.delete()
    }
}
