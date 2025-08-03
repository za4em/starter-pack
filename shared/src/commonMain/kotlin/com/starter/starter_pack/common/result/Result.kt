package com.starter.starter_pack.common.result

import kotlinx.coroutines.TimeoutCancellationException
import kotlin.coroutines.cancellation.CancellationException


data class Result<out T>(val data: T?, val error: Throwable?) {

    companion object {

        fun <T> empty(): Result<T> {
            return Result(data = null, error = null)
        }

        fun <T> success(data: T): Result<T> {
            return Result(data = data, error = null)
        }

        fun <T> error(error: Throwable, data: T? = null): Result<T> {
            return Result(data = data, error = error)
        }
    }

    val isLoading
        get() = data == null && error == null

    val isError
        get() = error != null

    val isSuccess
        get() = data != null
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (data != null) block(data)
    return this
}

inline fun <T> Result<T>.onError(block: (Throwable) -> Unit): Result<T> {
    if (error != null) block(error)
    return this
}

inline fun <T, R> Result<T>.mapSuccess(block: (T) -> R): Result<R> {
    return when {
        data != null -> Result.success(block(data))
        error != null -> Result.error(error)
        else -> Result.empty()

    }
}

fun <T> Result<T>.unwrap(): T {
    return data ?: throw error!!
}

inline fun <R> catching(converter: (Throwable) -> Throwable = { it }, block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (exception: TimeoutCancellationException) {
        Result.error(exception)
    } catch (exception: CancellationException) {
        throw exception
    } catch (throwable: Throwable) {
        Result.error(converter(throwable))
    }
}
