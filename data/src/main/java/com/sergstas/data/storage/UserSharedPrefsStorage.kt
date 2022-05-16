package com.sergstas.data.storage

import android.content.SharedPreferences
import com.sergstas.domain.models.UserData
import javax.inject.Inject

class UserSharedPrefsStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences,
): IUserStorage {
    companion object {
        private const val USER_KEY = "user"
    }

    override suspend fun loginUser(user: UserData) =
        sharedPreferences.edit().putString(USER_KEY, user.name).apply()

    override suspend fun logoutUser() =
        sharedPreferences.edit().putString(USER_KEY, null).apply()

    override suspend fun getUser() =
        sharedPreferences.getString(USER_KEY, null)?.run { UserData(this) }
}