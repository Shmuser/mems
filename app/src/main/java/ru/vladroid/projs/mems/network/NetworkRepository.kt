package ru.vladroid.projs.mems.network

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vladroid.projs.mems.domain.AuthData
import ru.vladroid.projs.mems.domain.Mem
import ru.vladroid.projs.mems.network.request.AuthRequest
import ru.vladroid.projs.mems.utils.mappers.AuthMapper
import ru.vladroid.projs.mems.utils.mappers.ListMemMapper
import ru.vladroid.projs.mems.utils.mappers.MemMapper

class NetworkRepository {
    private val memesApi = NetworkApiService.getApiInstance()
    private val authMapper = AuthMapper()
    private val memMapper = ListMemMapper(MemMapper())

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

    fun getMemes(): Single<Result<List<Mem>>> {
        return memesApi.getMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Result.success(memMapper.map(it))
            }
            .onErrorReturn {
                Result.failure(it)
            }
    }
}