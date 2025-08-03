package com.starter.starter_pack.core.storage

import com.starter.starter_pack.core.models.User
import io.github.xxfast.kstore.KStore

var appStorage: String = ""

expect val userStore: KStore<User>