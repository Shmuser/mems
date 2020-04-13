package ru.vladroid.projs.mems.network

class AuthInfo(
    val login: String,
    val password: String
)

class AuthResponse(
    val accessToken: String,
    val userInfo: UserInfo?
)

class UserInfo(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val userDescription: String
)

class Mem(
    val id: Long,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val createdDate: Long,
    val photoUrl: String
)