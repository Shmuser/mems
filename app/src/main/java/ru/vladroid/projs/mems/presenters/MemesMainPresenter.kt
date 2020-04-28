package ru.vladroid.projs.mems.presenters

import io.reactivex.disposables.CompositeDisposable
import ru.vladroid.projs.mems.network.NetworkRepository
import ru.vladroid.projs.mems.views.MemesMainView

interface MemesMainPresenter {

    fun attachView(memesMainView: MemesMainView)

    fun detachView()

    fun onFirstMemesLoading()

    fun reloadMemesList()
}


class MemesMainPresenterImpl : MemesMainPresenter {
    private var memesMainView: MemesMainView? = null
    private val disposables = CompositeDisposable()
    private val networkRepository = NetworkRepository()

    override fun attachView(memesMainView: MemesMainView) {
        this.memesMainView = memesMainView
    }

    override fun detachView() {
        disposables.dispose()
        memesMainView = null
    }

    override fun onFirstMemesLoading() {
        memesMainView?.showInitialLoading()
        disposables.add(
            networkRepository.getMemes()
                .subscribe { it ->
                    if (it.isSuccess) {
                        memesMainView?.hideInitialLoading()
                        memesMainView?.setMemesListData(it.getOrNull()!!)
                    } else {
                        memesMainView?.hideInitialLoading()
                        memesMainView?.onMemesLoadingError()
                    }
                }
        )
    }

    override fun reloadMemesList() {
        disposables.addAll(
            networkRepository.getMemes()
                .subscribe { it ->
                    if (it.isSuccess) {
                        memesMainView?.hideReloadMemesListProgress()
                        memesMainView?.setMemesListData(it.getOrNull()!!)
                    } else {
                        memesMainView?.hideReloadMemesListProgress()
                        memesMainView?.onMemesReloadingError()
                    }
                }
        )
    }
}