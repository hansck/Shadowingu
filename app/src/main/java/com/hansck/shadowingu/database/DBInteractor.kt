package com.hansck.shadowingu.database

import com.hansck.shadowingu.model.*
import com.hansck.shadowingu.presentation.App
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.INSERT_UPDATE_USER)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.INSERT_UPDATE_USER, e)
                    }
                })
    }

    fun getUsers() {
        App.database?.userDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ users -> listener.onQuerySucceed(QueryEnum.GET_USERS) })
    }
    //endregion

    //region Stage
    fun insertStages(vararg stages: Stage) {
        Completable.fromAction { App.database?.stageDao()?.insertAll(*stages) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.INSERT_STAGES)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.INSERT_STAGES, e)
                    }
                })
    }

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
                ?.subscribe({ stages -> listener.onQuerySucceed(QueryEnum.GET_STAGES) })
    }
    //endregion

    //region Audio
    fun insertAudios(vararg audios: Audio) {
        Completable.fromAction { App.database?.audioDao()?.insertAll(*audios) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.UPDATE_AUDIO)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.UPDATE_AUDIO, e)
                    }
                })
    }

    fun getAudios() {
        App.database?.audioDao()?.getAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ audios -> listener.onQuerySucceed(QueryEnum.GET_AUDIOS) })
    }
    //endregion

    //regionAvatar
    fun insertAvatars(vararg avatars: Avatar) {
        Completable.fromAction { App.database?.avatarDao()?.insertAll(*avatars) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.INSERT_AVATARS)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.INSERT_AVATARS, e)
                    }
                })
    }

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
                ?.subscribe({ avatars -> listener.onQuerySucceed(QueryEnum.GET_AVATARS) })
    }
    //endregion

    //region Title
    fun insertTitle(vararg titles: Title) {
        Completable.fromAction { App.database?.titleDao()?.insertAll(*titles) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.INSERT_TITLES)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.INSERT_TITLES, e)
                    }
                })
    }

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
                ?.subscribe({ titles -> listener.onQuerySucceed(QueryEnum.GET_TITLES) })
    }
    //endregion

    //regionBadge
    fun insertBadges(vararg badges: Badge) {
        Completable.fromAction { App.database?.badgeDao()?.insertAll(*badges) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        listener.onQuerySucceed(QueryEnum.INSERT_BADGES)
                    }

                    override fun onError(e: Throwable) {
                        listener.onQueryFailed(QueryEnum.INSERT_BADGES, e)
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
                ?.subscribe({ badges -> listener.onQuerySucceed(QueryEnum.GET_BADGES) })
    }
    //endregion
}