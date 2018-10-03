package com.hansck.shadowingu.screen.shop

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.ShopPresenter
import com.hansck.shadowingu.presentation.presenter.ShopPresenter.ShopView.ViewState.*


/**
 * Created by Hans CK on 07-Jun-18.
 */
class ShopPresenterImpl(val view: ShopPresenter.ShopView) : ShopPresenter, QueryListener {

	private var interactor = DBInteractor(this)

	override fun presentState(state: ShopPresenter.ShopView.ViewState) {
		Log.i(ShopFragment::class.java.simpleName, state.name)
		when (state) {
			IDLE -> view.showState(IDLE)
			LOADING -> view.showState(LOADING)
			BUY_AVATAR -> {
				presentState(LOADING)
				interactor.updateAvatar(view.doRetrieveModel().boughtAvatar)
			}
			UPDATE_GEM -> {
				presentState(LOADING)
				interactor.insertOrUpdateUser(view.doRetrieveModel().user)
			}
			UPDATE_BADGE -> {
				presentState(LOADING)
				// Unlock Rich Buyer Badge
				interactor.updateBadge(view.doRetrieveModel().badges[2])
			}
			SHOW_UPDATED_AVATARS -> view.showState(SHOW_UPDATED_AVATARS)
			SHOW_AVATARS -> view.showState(SHOW_AVATARS)
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
			QueryEnum.BUY_AVATAR -> {
				presentState(UPDATE_GEM)
			}
			QueryEnum.UPDATE_USER -> {
				if (view.doRetrieveModel().isFirstBuy) {
					presentState(UPDATE_BADGE)
				} else {
					presentState(SHOW_UPDATED_AVATARS)
				}
			}
			QueryEnum.UPDATE_BADGE -> {
				presentState(SHOW_UPDATED_AVATARS)
			}
		}
	}

	override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
		presentState(ERROR)
	}
}