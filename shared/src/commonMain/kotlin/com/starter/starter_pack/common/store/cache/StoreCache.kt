package com.starter.starter_pack.common.store.cache

import kotlinx.coroutines.flow.Flow

interface StoreCache<PARAMS, DATA> {

    fun observe(params: PARAMS): Flow<DATA?>

    suspend fun get(params: PARAMS): DATA?

    suspend fun set(params: PARAMS, data: DATA)

    suspend fun clear(params: PARAMS)

    suspend fun clearAll()
}
