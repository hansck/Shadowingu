package com.hansck.shadowingu.presentation.presenter

import com.hansck.shadowingu.presentation.base.BasePresenter
import com.hansck.shadowingu.screen.play.PlayViewModel

/**
 * Created by Hans CK on 07-Jun-18.
 */
interface PlayPresenter : BasePresenter {

	interface PlayView {
		/**
		 * This enum is used for determine the current state of this screen
		 */
		enum class ViewState {
			IDLE, LOADING, SHOW_WORD_SCREEN, SHOW_PLAY_RESULT, SHOW_GAME_OVER, BACK_TO_HOME, RESET_PLAY,
			SHOW_CORRECT, REDUCE_HEARTS, PLAYER_DEAD, UPDATE_USER, UPDATE_LESSON, UPDATE_BADGE,
			UPDATE_LESSONS_PASSED, SKIP_WORD, START_TIMER, SHOW_SCREEN_STATE, ERROR
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
		fun doRetrieveModel(): PlayViewModel
	}

	/**
	 * This method is used for present the current state of this screen
	 *
	 * @param state
	 */
	fun presentState(state: PlayView.ViewState)
}