package com.starter.starter_pack.core.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
)