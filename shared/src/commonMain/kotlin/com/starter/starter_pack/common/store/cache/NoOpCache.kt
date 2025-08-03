package com.starter.starter_pack.common.store.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class NoOpCache<PARAMS, DATA> : StoreCache<PARAMS, DATA> {

    private val emitter = MutableSharedFlow<Pair<PARAMS, DATA>>(replay = 1)

    override fun observe(params: PARAMS): Flow<DATA> {
        return emitter
            .filter { (param, _) -> param == params }
            .map { (_, data) -> data }
    }

    override suspend fun get(params: PARAMS): DATA? {
        return null
    }

    override suspend fun set(params: PARAMS, data: DATA) {
        emitter.emit(params to data)
    }

    override suspend fun clear(params: PARAMS) = Unit

    override suspend fun clearAll() = Unit
}
