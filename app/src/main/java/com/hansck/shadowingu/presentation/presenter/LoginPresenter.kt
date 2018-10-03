package com.hansck.shadowingu.presentation.presenter

import com.hansck.shadowingu.presentation.base.BasePresenter
import com.hansck.shadowingu.screen.login.LoginViewModel

/**
 * Created by Hans CK on 07-Jun-18.
 */
interface LoginPresenter : BasePresenter {

	interface LoginView {
		/**
		 * This enum is used for determine the current state of this screen
		 */
		enum class ViewState {
			IDLE, LOADING, ATTEMPT_LOGIN, UPDATE_USER, ENTER, SHOW_SCREEN_STATE, ERROR
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
		fun doRetrieveModel(): LoginViewModel
	}

	/**
	 * This method is used for present the current state of this screen
	 *
	 * @param state
	 */
	fun presentState(state: LoginView.ViewState)
}