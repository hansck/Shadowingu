package com.hansck.shadowingu.presentation.presenter

import com.hansck.shadowingu.presentation.base.BasePresenter
import com.hansck.shadowingu.screen.playword.PlayWordViewModel

/**
 * Created by Hans CK on 07-Jun-18.
 */
interface PlayWordPresenter : BasePresenter {

    interface PlayWordView {
        /**
         * This enum is used for determine the current state of this screen
         */
        enum class ViewState {
            IDLE, LOADING, SHOW_WORD, NEXT_WORD, SHOW_SCREEN_STATE, ERROR
        }

        enum class ScreenState {

        }

        /**
         * This method is to show the current state of this screen
         *
         * @param viewState
         */
        fun showState(viewState: ViewState)

        /**
         * This function return the model that was belong to this screen
         *
         * @return
         */
        fun doRetrieveModel(): PlayWordViewModel
    }

    /**
     * This method is used for present the current state of this screen
     *
     * @param state
     */
    fun presentState(state: PlayWordView.ViewState)
}