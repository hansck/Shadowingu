package com.hansck.shadowingu.screen.learnword

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.LearnWordPresenter
import com.hansck.shadowingu.presentation.presenter.LearnWordPresenter.LearnWordView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LearnWordPresenterImpl(val view: LearnWordPresenter.LearnWordView) : LearnWordPresenter, QueryListener {

	private var interactor = DBInteractor(this)

	override fun presentState(state: LearnWordPresenter.LearnWordView.ViewState) {
		Log.i(LearnWordActivity::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			SHOW_WORD -> view.showState(SHOW_WORD)
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