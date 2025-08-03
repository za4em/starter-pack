package com.starter.starter_pack.common.store.network

fun interface StoreUpdater<KEY, PARAMS, DATA> {

    suspend operator fun invoke(key: KEY, params: PARAMS): DATA
}
