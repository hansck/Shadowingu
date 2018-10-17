package com.hansck.shadowingu.screen.learn

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.LearnPresenter
import com.hansck.shadowingu.presentation.presenter.LearnPresenter.LearnView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LearnPresenterImpl(val view: LearnPresenter.LearnView) : LearnPresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: LearnPresenter.LearnView.ViewState) {
        Log.i(LearnFragment::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            SHOW_STAGES -> view.showState(SHOW_STAGES)
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
        if (route == QueryEnum.GET_LESSONS) {
            presentState(SHOW_STAGES)
        }
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        presentState(ERROR)
    }
}