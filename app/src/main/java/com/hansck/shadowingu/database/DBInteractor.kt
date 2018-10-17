package com.hansck.shadowingu.database

import android.annotation.SuppressLint
import com.hansck.shadowingu.model.Avatar
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.model.Lesson
import com.hansck.shadowingu.model.User
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

	@SuppressLint("CheckResult")
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

	//region Lesson
	fun updateLessons(lesson: Lesson) {
		Completable.fromAction { App.database?.lessonDao()?.updateStage(lesson) }
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : CompletableObserver {
					override fun onSubscribe(d: Disposable) {

					}

					override fun onComplete() {
						listener.onQuerySucceed(QueryEnum.UPDATE_LESSONS)
					}

					override fun onError(e: Throwable) {
						listener.onQueryFailed(QueryEnum.UPDATE_LESSONS, e)
					}
				})
	}

	@SuppressLint("CheckResult")
	fun getLessons() {
		App.database?.lessonDao()?.getAll()
				?.subscribeOn(Schedulers.io())
				?.observeOn(AndroidSchedulers.mainThread())
				?.subscribe { lessons ->
					run {
						DataManager.instance.addLessons(lessons)
						listener.onQuerySucceed(QueryEnum.GET_LESSONS)
					}
				}
	}
	//endregion

	//region Word
	@SuppressLint("CheckResult")
	fun getWords() {
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

	@SuppressLint("CheckResult")
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
	@SuppressLint("CheckResult")
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

	@SuppressLint("CheckResult")
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
	@SuppressLint("CheckResult")
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