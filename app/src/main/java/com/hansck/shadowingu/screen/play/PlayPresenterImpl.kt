package com.hansck.shadowingu.screen.play

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayPresenter.PlayView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayPresenterImpl(val view: PlayPresenter.PlayView) : PlayPresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: PlayPresenter.PlayView.ViewState) {
        Log.i(PlayActivity::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            SHOW_WORD_SCREEN -> view.showState(SHOW_WORD_SCREEN)
            SHOW_PLAY_RESULT -> view.showState(SHOW_PLAY_RESULT)
            SHOW_GAME_OVER -> view.showState(SHOW_GAME_OVER)
            BACK_TO_HOME -> view.showState(BACK_TO_HOME)
            RESET_PLAY -> view.showState(RESET_PLAY)
            SHOW_CORRECT -> view.showState(SHOW_CORRECT)
            SHOW_WRONG -> view.showState(SHOW_WRONG)
            UPDATE_USER -> interactor.insertOrUpdateUser(view.doRetrieveModel().user)
            UPDATE_STAGE -> interactor.updateStage(view.doRetrieveModel().stage)
            UPDATE_BADGE -> interactor.updateBadges(view.doRetrieveModel().updatedBadges)
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
            QueryEnum.UPDATE_USER -> presentState(UPDATE_STAGE)
            QueryEnum.UPDATE_STAGE -> presentState(UPDATE_BADGE)
            QueryEnum.UPDATE_BADGE -> {
                if (view.doRetrieveModel().isGameOver) {
                    presentState(SHOW_GAME_OVER)
                } else {
                    presentState(SHOW_PLAY_RESULT)
                }
            }
            else -> presentState(IDLE)
        }
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        presentState(ERROR)
    }
}