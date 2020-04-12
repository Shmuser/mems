package ru.vladroid.projs.mems.presenters

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vladroid.projs.mems.network.AuthInfo
import ru.vladroid.projs.mems.network.NetworkService
import ru.vladroid.projs.mems.utils.SharedPrefsHelper
import ru.vladroid.projs.mems.views.AuthView

interface AuthPresenter {

    fun attachView(authView: AuthView)

    fun detachView()

    fun onAuthButtonClick(authInfo: AuthInfo)

}

class AuthPresenterImpl(context: Context) : AuthPresenter {
    private var authView: AuthView? = null
    private val sharedPrefsHelper = SharedPrefsHelper(context)
    private val memesApi = NetworkService.getApiInstance()
    private lateinit var disposableAuth: Disposable


    override fun attachView(authView: AuthView) {
        this.authView = authView
    }

    override fun detachView() {
        authView = null
        disposePrevRequest()
    }

    override fun onAuthButtonClick(authInfo: AuthInfo) {
        authView?.showAuthLoading()
        disposePrevRequest()
        disposableAuth = memesApi.login(authInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sharedPrefsHelper.putAuthResponse(it)
                authView?.hideAuthLoading()
                authView?.onWrongAuthInfoError()
            }, {
                authView?.hideAuthLoading()
                authView?.onWrongAuthInfoError()
            })
    }

    private fun disposePrevRequest() {
        if (this::disposableAuth.isInitialized)
            disposableAuth.dispose()
    }
}