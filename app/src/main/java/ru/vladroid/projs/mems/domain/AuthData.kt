package ru.vladroid.projs.mems.domain

import ru.vladroid.projs.mems.network.response.UserInfo

data class AuthData (
    val accessToken: String,
    val userInfo: UserInfo
)