package ru.vladroid.projs.mems.presenters

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.vladroid.projs.mems.network.NetworkService
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
            NetworkService.getApiInstance().getMemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    memesMainView?.hideInitialLoading()
                    memesMainView?.setMemesListData(it)
                }, {
                    memesMainView?.hideInitialLoading()
                    memesMainView?.onMemesLoadingError()
                })
        )
    }

    override fun reloadMemesList() {
        disposables.addAll(
            NetworkService.getApiInstance().getMemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    memesMainView?.hideReloadMemesListProgress()
                    memesMainView?.setMemesListData(it)
                }, {
                    memesMainView?.hideReloadMemesListProgress()
                    memesMainView?.onMemesReloadingError()
                })
        )
    }
}