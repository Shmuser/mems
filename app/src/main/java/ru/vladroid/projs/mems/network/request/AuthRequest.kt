package ru.vladroid.projs.mems.network.request

data class AuthRequest(
    val login: String,
    val password: String
)