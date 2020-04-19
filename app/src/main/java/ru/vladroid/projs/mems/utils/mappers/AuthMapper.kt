package ru.vladroid.projs.mems.utils.mappers

import ru.vladroid.projs.mems.domain.AuthData
import ru.vladroid.projs.mems.network.response.AuthResponse
import ru.vladroid.projs.mems.network.response.UserInfo

class AuthMapper : Mapper<AuthResponse, AuthData> {
    override fun map(input: AuthResponse): AuthData {
        var userInfo = UserInfo.EMPTY
        input.userInfo?.let {
            userInfo = UserInfo(
                it.id,
                it.username,
                it.firstName,
                it.lastName,
                it.userDescription
            )
        }
        return AuthData(input.accessToken, userInfo)
    }
}