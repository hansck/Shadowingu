package com.hansck.shadowingu

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hansck.shadowingu.screen.login.LoginActivity
import com.hansck.shadowingu.screen.main.MainActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Hans CK on 18-Jul-18.
 */
@RunWith(AndroidJUnit4::class)
class PlayTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    var loginActivityTestRule = ActivityTestRule(LoginActivity::class.java, true, true)

    @Test
    fun playGameAndWin() {
        onView(allOf<View>(isDisplayed(), withId(R.id.stageList)))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        for (i in 0..9) {
            onView(withId(R.id.btnRecording)).perform(click())
        }

        onView(withId(R.id.btnContinue)).perform(click())
    }

    @Test
    fun playGameAndLose() {
        onView(allOf<View>(isDisplayed(), withId(R.id.stageList)))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        for (i in 0..2) {
            onView(withId(R.id.btnVoice)).perform(click())
        }

        onView(withId(R.id.btnContinue)).perform(click())
    }
}
