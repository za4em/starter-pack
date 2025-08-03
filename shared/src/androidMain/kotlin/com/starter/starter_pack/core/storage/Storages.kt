package com.starter.starter_pack.core.storage

import com.starter.starter_pack.core.models.User
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

actual val userStore: KStore<User> by lazy {
    storeOf(file = Path("${appStorage}/user.json"))
}
