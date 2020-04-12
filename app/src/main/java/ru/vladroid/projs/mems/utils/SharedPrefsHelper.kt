package ru.vladroid.projs.mems.utils

import android.content.Context
import ru.vladroid.projs.mems.network.AuthResponse
import ru.vladroid.projs.mems.network.UserInfo

class SharedPrefsHelper(context: Context) {
    private val storageName = "mems_user_info"
    private val accessTokenKey = "mems_user_info_access_token"
    private val idKey = "mems_user_info_id"
    private val usernameKey = "mems_user_info_username"
    private val firstNameKey = "mems_user_info_first_name"
    private val lastNameKey = "mems_user_info_last_name"
    private val userDescriptionKey = "mems_user_info_user_description"
    private val sp = context.getSharedPreferences(storageName, Context.MODE_PRIVATE)

    fun putAuthResponse(authResponse: AuthResponse) {
        val editor = sp.edit()
        editor.putString(accessTokenKey, authResponse.accessToken)
        if (authResponse.userInfo != null) {
            editor.putInt(idKey, authResponse.userInfo.id)
            editor.putString(usernameKey, authResponse.userInfo.username)
            editor.putString(firstNameKey, authResponse.userInfo.firstName)
            editor.putString(lastNameKey, authResponse.userInfo.lastName)
            editor.putString(userDescriptionKey, authResponse.userInfo.userDescription)
        }
        editor.apply()
    }

    fun getAuthResponse(): AuthResponse {
        val accessToken = sp.getString(accessTokenKey, "qwerty")!!
        val id = sp.getInt(idKey, -1)
        val username = sp.getString(usernameKey, "nouser")!!
        val firstName = sp.getString(firstNameKey, "FirstName")!!
        val lastName = sp.getString(lastNameKey, "LastName")!!
        val userDescription = sp.getString(userDescriptionKey, "Hi, i'm nouser")!!
        val userInfo = UserInfo(id, username, firstName, lastName, userDescription)
        return AuthResponse(accessToken, userInfo)
    }
}