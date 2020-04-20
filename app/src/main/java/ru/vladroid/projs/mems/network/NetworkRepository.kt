package ru.vladroid.projs.mems.network

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vladroid.projs.mems.domain.AuthData
import ru.vladroid.projs.mems.network.request.AuthRequest
import ru.vladroid.projs.mems.utils.mappers.AuthMapper

class NetworkRepository {
    private val memesApi = NetworkApiService.getApiInstance()
    private val authMapper = AuthMapper()

    fun login(authRequest: AuthRequest): Single<Result<AuthData>> {
        return memesApi.login(authRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Result.success(authMapper.map(it))
            }
            .onErrorReturn {
                Result.failure(it)
            }

    }
}