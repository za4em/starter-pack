package com.starter.starter_pack.common.store.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update

class InMemoryCache<PARAMS, DATA> : StoreCache<PARAMS, DATA> {

    private val memory = MutableStateFlow(mapOf<PARAMS, DATA>())

    override fun observe(params: PARAMS): Flow<DATA> {
        return memory.mapNotNull { it[params] }
    }

    override suspend fun get(params: PARAMS): DATA? {
        return memory.value[params]
    }

    override suspend fun set(params: PARAMS, data: DATA) {
        memory.update {
            val map = it.toMutableMap()
            map[params] = data
            map
        }
    }

    override suspend fun clear(params: PARAMS) {
        memory.update {
            val map = it.toMutableMap()
            map.remove(params)
            map
        }
    }

    override suspend fun clearAll() {
        memory.update { mapOf() }
    }
}
