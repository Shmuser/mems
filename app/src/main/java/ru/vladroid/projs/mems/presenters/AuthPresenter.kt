package ru.vladroid.projs.mems.presenters

import android.content.Context
import io.reactivex.disposables.Disposable
import ru.vladroid.projs.mems.domain.AuthData
import ru.vladroid.projs.mems.network.NetworkRepository
import ru.vladroid.projs.mems.network.request.AuthRequest
import ru.vladroid.projs.mems.utils.App
import ru.vladroid.projs.mems.utils.AppConstants
import ru.vladroid.projs.mems.views.AuthView

interface AuthPresenter {

    fun attachView(authView: AuthView)

    fun detachView()

    fun onAuthButtonClick(login: String, password: String)

}

class AuthPresenterImpl(context: Context) : AuthPresenter {
    private var authView: AuthView? = null
    private val sharedPrefUtils = App.getInstance()
    private val networkRepository = NetworkRepository()
    private lateinit var disposableAuth: Disposable


    override fun attachView(authView: AuthView) {
        this.authView = authView
    }

    override fun detachView() {
        authView = null
        disposePrevRequest()
    }

    override fun onAuthButtonClick(login: String, password: String) {
        val authRequest =
            AuthRequest(login, password)
        authView?.showAuthLoading()
        disposePrevRequest()
        disposableAuth = networkRepository.login(authRequest)
            .subscribe { t ->
                if (t.isSuccess) {
                    saveAuthInfo(t.getOrNull()!!)
                    authView?.hideAuthLoading()
                    authView?.onAuthSuccess()
                } else {
                    authView?.hideAuthLoading()
                    authView?.onWrongAuthInfoError()
                }
            }

    }

    private fun saveAuthInfo(authData: AuthData) {
        sharedPrefUtils.putString(AppConstants.ACCESS_TOKEN_KEY, authData.accessToken)
        sharedPrefUtils.putInt(AppConstants.ID_KEY, authData.userInfo.id)
        sharedPrefUtils.putString(AppConstants.USERNAME_KEY, authData.userInfo.username)
        sharedPrefUtils.putString(AppConstants.FIRSTNAME_KEY, authData.userInfo.firstName)
        sharedPrefUtils.putString(AppConstants.LASTNAME_KEY, authData.userInfo.lastName)
        sharedPrefUtils.putString(
            AppConstants.USER_DESCRIPTION_KEY,
            authData.userInfo.userDescription
        )
    }

    private fun disposePrevRequest() {
        if (this::disposableAuth.isInitialized)
            disposableAuth.dispose()
    }
}