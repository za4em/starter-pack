package com.starter.starter_pack.common.datasource.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Storage<T> {
    fun flow(): Flow<T?>

    fun stateFlow(): StateFlow<T?>

    fun valueOrNull(): T?

    suspend fun update(newValue: T?)
}
