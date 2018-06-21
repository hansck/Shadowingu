package com.hansck.shadowingu.screen.home

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.presenter.HomePresenter
import com.hansck.shadowingu.presentation.presenter.HomePresenter.HomeView.ViewState.*
import com.hansck.shadowingu.util.QueryListener

/**
 * Created by Hans CK on 07-Jun-18.
 */
class HomePresenterImpl(val view: HomePresenter.HomeView) : HomePresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: HomePresenter.HomeView.ViewState) {
        Log.i(HomeFragment::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            SHOW_ITEMS -> view.showState(SHOW_ITEMS)
            SHOW_SCREEN_STATE -> view.showState(SHOW_SCREEN_STATE)
            ERROR -> view.showState(ERROR)
        }
    }

    override fun onAttach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQuerySucceed(route: QueryEnum) {
        if (route == QueryEnum.GET_STAGES) {
            presentState(SHOW_ITEMS)
        }
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        presentState(ERROR)
    }
}