package com.hansck.shadowingu.screen.main

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.MainPresenter
import com.hansck.shadowingu.presentation.presenter.MainPresenter.MainView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class MainPresenterImpl(val view: MainPresenter.MainView) : MainPresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: MainPresenter.MainView.ViewState) {
        Log.i(MainActivity::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            LOAD_USER -> {
                presentState(LOADING)
                interactor.getUsers()
            }
            LOAD_STAGES -> interactor.getStages()
            LOAD_TITLES -> interactor.getTitles()
            LOAD_AUDIOS -> interactor.getAudios()
            LOAD_LEVELS -> interactor.getLevels()
            LOAD_TAB -> view.showState(LOAD_TAB)
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
        when (route) {
            QueryEnum.GET_USERS -> presentState(LOAD_STAGES)
            QueryEnum.GET_STAGES -> presentState(LOAD_TITLES)
            QueryEnum.GET_TITLES -> presentState(LOAD_AUDIOS)
            QueryEnum.GET_WORDS -> presentState(LOAD_LEVELS)
            QueryEnum.GET_LEVELS -> presentState(LOAD_TAB)
            else -> {
                presentState(LOAD_TAB)
            }
        }
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        presentState(ERROR)
    }
}