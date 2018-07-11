package com.hansck.shadowingu.screen.playword

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter.PlayWordView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayWordPresenterImpl(val view: PlayWordPresenter.PlayWordView) : PlayWordPresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: PlayWordPresenter.PlayWordView.ViewState) {
        Log.i(PlayWordFragment::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            SHOW_WORD -> view.showState(SHOW_WORD)
            CORRECT_ANSWER -> view.showState(CORRECT_ANSWER)
            WRONG_ANSWER -> view.showState(WRONG_ANSWER)
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
        if (route == QueryEnum.GET_WORDS) {
            presentState(SHOW_WORD)
        }
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        presentState(ERROR)
    }
}