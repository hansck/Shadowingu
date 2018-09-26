package com.hansck.shadowingu.database

import com.hansck.shadowingu.model.*
import com.hansck.shadowingu.presentation.App
import com.hansck.shadowingu.presentation.customview.QueryListener
import com.hansck.shadowingu.util.DataManager
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
    fun insertOrUpdateUser(user: User) {
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
                        listener.onQuerySucceed(QueryEnum.GET_USERS)
                    }
                }
    }
    //endregion

    //region Topic
    fun updateStage(topic: Topic) {
        Completable.fromAction { App.database?.stageDao()?.updateStage(topic) }
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
                        listener.onQuerySucceed(QueryEnum.GET_WORDS)
                    }
                }
    }
    //endregion

    //region Avatar
    fun updateAvatar(avatar: Avatar) {
        Completable.fromAction { App.database?.avatarDao()?.updateAvatar(avatar) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.BUY_AVATAR)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.BUY_AVATAR, e)
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
                        listener.onQuerySucceed(QueryEnum.GET_TITLES)
                    }
                }
    }
    //endregion

    //region Badge
    fun updateBadges(badges: List<Badge>) {
        Completable.fromAction { App.database?.badgeDao()?.updateBadges(badges) }
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
                        listener.onQuerySucceed(QueryEnum.GET_BADGES)
                    }
                }
    }
    //endregion

    //region Level
    fun getLevels() {
        App.database?.levelDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { levels ->
                    run {
                        DataManager.instance.addLevels(levels)
                        listener.onQuerySucceed(QueryEnum.GET_LEVELS)
                    }
                }
    }
    //endregion
}