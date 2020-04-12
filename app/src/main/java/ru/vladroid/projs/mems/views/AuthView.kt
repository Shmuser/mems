package ru.vladroid.projs.mems.views

interface AuthView {

    fun onWrongAuthInfoError()

    fun onAuthSuccess()

    fun showAuthLoading()

    fun hideAuthLoading()
}