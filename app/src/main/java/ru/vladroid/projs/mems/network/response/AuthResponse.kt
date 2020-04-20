package ru.vladroid.projs.mems.network.response

data class AuthResponse(
    val accessToken: String,
    val userInfo: UserInfo?
)

data class UserInfo(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val userDescription: String
) {
    companion object {
        val EMPTY = UserInfo(-1, "noname", "firstName", "lastName", "desc")
    }
}