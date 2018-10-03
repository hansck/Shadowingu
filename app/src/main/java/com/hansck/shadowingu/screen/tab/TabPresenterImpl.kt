package com.hansck.shadowingu.screen.tab

import android.util.Log
import com.hansck.shadowingu.presentation.presenter.TabPresenter
import com.hansck.shadowingu.presentation.presenter.TabPresenter.TabView.ViewState.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class TabPresenterImpl(val view: TabPresenter.TabView) : TabPresenter {

	override fun presentState(state: TabPresenter.TabView.ViewState) {
		Log.i(TabFragment::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			SHOW_TAB -> view.showState(SHOW_TAB)
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