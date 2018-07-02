package com.hansck.shadowingu.screen.leaderboard

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.hansck.shadowingu.database.DBInteractor
import com.hansck.shadowingu.database.QueryEnum
import com.hansck.shadowingu.model.LeaderboardUser
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.presentation.presenter.LeaderboardPresenter
import com.hansck.shadowingu.presentation.presenter.LeaderboardPresenter.LeaderboardView.ViewState.*
import com.hansck.shadowingu.util.*
import java.util.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LeaderboardPresenterImpl(val view: LeaderboardPresenter.LeaderboardView) : LeaderboardPresenter, QueryListener {

    private var interactor = DBInteractor(this)

    override fun presentState(state: LeaderboardPresenter.LeaderboardView.ViewState) {
        Log.i(LeaderboardFragment::class.java.simpleName, state.name)
        when (state) {
            IDLE -> view.showState(IDLE)
            LOADING -> view.showState(LOADING)
            UPDATE_USER -> updateLeaderboardUser(view.doRetrieveModel().currentUser)
            LOAD_LEADERBOARD -> {
                getUsers()
            }
            SHOW_LEADERBOARD -> view.showState(SHOW_LEADERBOARD)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryFailed(route: QueryEnum, throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateLeaderboardUser(user: LeaderboardUser) {
        val ref = FirebaseDB.instance.getDbReference(Constants.Database.USER)
        val taskMap = HashMap<String, Any>()
        taskMap["email"] = user.email
        taskMap["name"] = user.name
        taskMap["level"] = user.level
        taskMap["image"] = user.image
        taskMap["badges"] = user.badges
        ref.child(PersistentManager.instance.getUserKey()).updateChildren(taskMap)
        presentState(LOAD_LEADERBOARD)
    }

    private fun getUsers() {
        var count = 1
        val ref = FirebaseDB.instance.getDbReference(Constants.Database.USER)
        ref.orderByChild(Constants.Database.LEVEL).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                DataManager.instance.leaderboardUsers.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val user = postSnapshot.getValue(LeaderboardUser::class.java)
                    user!!.rank = count
                    if (count <= Constants.General.MAX_LEADERBOARD || user.email == AuthManager.instance.account.email) {
                        DataManager.instance.addLeaderboardUser(user)
                    }
                    count++
                }
                presentState(SHOW_LEADERBOARD)
            }

            override fun onCancelled(error: DatabaseError) {
                presentState(ERROR)
            }
        })
    }
}