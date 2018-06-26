package com.hansck.shadowingu.database

import android.util.Log
import com.hansck.shadowingu.model.*
import com.hansck.shadowingu.presentation.App
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.QueryListener
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Hans CK on 13-Jun-18.
 */
class DBInteractor(var listener: QueryListener) {

    //region User
    fun insertorUpdateUser(user: User) {
        Completable.fromAction { App.database?.userDao()?.insert(user) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.UPDATE_USER)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.UPDATE_USER, e)
                    }
                })
    }

    fun getUsers() {
        App.database?.userDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { users ->
                    run {
                        DataManager.instance.user = users[0]
                        Log.e("Users", users[0].name)
                        listener.onQuerySucceed(QueryEnum.GET_USERS)
                    }
                }
    }
    //endregion

    //region Stage
    fun updateStage(stage: Stage) {
        Completable.fromAction { App.database?.stageDao()?.updateStage(stage) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.UPDATE_STAGE)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.UPDATE_STAGE, e)
                    }
                })
    }

    fun getStages() {
        App.database?.stageDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { stages ->
                    run {
                        DataManager.instance.addStages(stages)
                        Log.e("stages", stages[4].category)
                        listener.onQuerySucceed(QueryEnum.GET_STAGES)
                    }
                }
    }
    //endregion

    //region Audio
    fun getAudios() {
        App.database?.audioDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { audios ->
                    run {
                        DataManager.instance.addWords(audios)
                        Log.e("audio", audios[4].furigana)
                        listener.onQuerySucceed(QueryEnum.GET_WORDS)
                    }
                }
    }
    //endregion

    //regionAvatar
    fun updateAvatar(avatar: Avatar) {
        Completable.fromAction { App.database?.avatarDao()?.updateAvatar(avatar) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.UPDATE_AVATAR)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.UPDATE_AVATAR, e)
                    }
                })
    }

    fun getAvatars() {
        App.database?.avatarDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { avatars ->
                    run {
                        DataManager.instance.addAvatars(avatars)
                        Log.e("avatar", avatars[2].name)
                        listener.onQuerySucceed(QueryEnum.GET_AVATARS)
                    }
                }
    }
    //endregion

    //region Title
    fun updateTitle(title: Title) {
        Completable.fromAction { App.database?.titleDao()?.updateTitle(title) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.UPDATE_TITLE)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.UPDATE_TITLE, e)
                    }
                })
    }

    fun getTitles() {
        App.database?.titleDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { titles ->
                    run {
                        DataManager.instance.addTitles(titles)
                        Log.e("title", titles[2].name)
                        listener.onQuerySucceed(QueryEnum.GET_TITLES)
                    }
                }
    }
    //endregion

    //regionBadge
    fun updateBadge(badge: Badge) {
        Completable.fromAction { App.database?.badgeDao()?.updateBadge(badge) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.UPDATE_BADGE)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.UPDATE_BADGE, e)
                    }
                })
    }

    fun getBadges() {
        App.database?.badgeDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { badges ->
                    run {
                        DataManager.instance.addBadges(badges)
                        Log.e("badges", badges[2].name)
                        listener.onQuerySucceed(QueryEnum.GET_BADGES)
                    }
                }
    }
    //endregion
}