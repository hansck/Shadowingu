package com.hansck.shadowingu.screen.login

import android.util.Log
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.model.User
import com.hansck.shadowingu.presentation.presenter.LoginPresenter
import com.hansck.shadowingu.presentation.presenter.LoginPresenter.LoginView.ViewState.*
import com.hansck.shadowingu.util.AuthManager
import com.hansck.shadowingu.presentation.customview.QueryListener

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LoginPresenterImpl(val view: LoginPresenter.LoginView) : LoginPresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: LoginPresenter.LoginView.ViewState) {
        Log.i(LoginViewModel::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            ATTEMPT_LOGIN -> {
                presentState(LOADING)
                view.showState(ATTEMPT_LOGIN)
            }
            ENTER -> {
                view.showState(ENTER)
            }
            UPDATE_USER -> {
                val user = User(1, AuthManager.instance.account.displayName!!, 1, 0, 0,
                        AuthManager.instance.account.photoUrl.toString())
                interactor.insertOrUpdateUser(user)
            }
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
        if (route == QueryEnum.UPDATE_USER) {
            presentState(ENTER)
        }
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        presentState(ERROR)
    }
}