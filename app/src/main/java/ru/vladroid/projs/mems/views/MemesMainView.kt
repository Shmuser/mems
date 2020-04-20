package ru.vladroid.projs.mems.views

import ru.vladroid.projs.mems.network.Mem

interface MemesMainView {

    fun showInitialLoading()

    fun hideInitialLoading()

    fun onMemesLoadingError()

    fun setMemesListData(memes: ArrayList<Mem>)

    fun onReloadMemesList()

    fun hideReloadMemesListProgress()

    fun onMemesReloadingError()
}