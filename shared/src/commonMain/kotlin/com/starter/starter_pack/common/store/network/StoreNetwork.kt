package com.starter.starter_pack.common.store.network

fun interface StoreNetwork<PARAMS, DATA> {

    suspend operator fun invoke(params: PARAMS): DATA
}
