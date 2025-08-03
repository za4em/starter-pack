package com.starter.starter_pack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform