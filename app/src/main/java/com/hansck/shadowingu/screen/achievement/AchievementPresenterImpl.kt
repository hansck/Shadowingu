package com.hansck.shadowingu.screen.achievement

import android.util.Log
import com.hansck.shadowingu.presentation.presenter.AchievementPresenter
import com.hansck.shadowingu.presentation.presenter.AchievementPresenter.AchievementView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class AchievementPresenterImpl(val view: AchievementPresenter.AchievementView) : AchievementPresenter {

	override fun presentState(state: AchievementPresenter.AchievementView.ViewState) {
		Log.i(AchievementActivity::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			SHOW_ACHIEVEMENT -> view.showState(SHOW_ACHIEVEMENT)
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
}