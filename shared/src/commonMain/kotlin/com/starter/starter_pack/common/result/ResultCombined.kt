package com.starter.starter_pack.common.result


data class ResultCombined(val results: List<Result<Any?>>) {
    val isError
        get() = results.any { it.isError }

    fun throwableOrNull() = results.find { it.error != null }?.error

    val isLoading
        get() = !isError && results.any { it.isLoading }

    val isSuccess
        get() = results.all { it.isSuccess }
}

fun ResultCombined(vararg results: Result<Any?>) = ResultCombined(results.toList())
