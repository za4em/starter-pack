package com.starter.starter_pack.common.datasource.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class InMemoryStorage<T>(defaultValue: T? = null) : Storage<T> {

    private val mutableStateFlow: MutableStateFlow<T?> = MutableStateFlow(defaultValue)

    override fun flow(): Flow<T?> {
        return mutableStateFlow.asStateFlow()
    }

    override fun stateFlow(): StateFlow<T?> {
        return mutableStateFlow
    }

    override suspend fun update(newValue: T?) {
        mutableStateFlow.emit(newValue)
    }

    override fun valueOrNull(): T? {
        return mutableStateFlow.value
    }
}
