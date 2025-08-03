package com.starter.starter_pack.common.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

suspend fun <T> io(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO) { this.block() }
}

suspend fun <T> main(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main) { this.block() }
}
