package com.hansck.shadowingu

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.hansck.shadowingu.screen.login.LoginActivity
import com.hansck.shadowingu.screen.main.MainActivity
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Hans CK on 23-Jul-18.
 */
@RunWith(AndroidJUnit4::class)
class ShopTest {

    val ctx = InstrumentationRegistry.getContext()

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    var loginActivityTestRule = ActivityTestRule(LoginActivity::class.java, true, true)

    @Test
    fun buyAvatar() {

        Espresso.onView(AllOf.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withText(ctx.getString(R.string.shop)))).perform(ViewActions.click())

        Espresso.onView(AllOf.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.avatarList)))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, MyViewAction.clickChildViewWithId(R.id.btnBuy)))

        Espresso.onView(ViewMatchers.withId(R.id.gem)).check(ViewAssertions.matches(ViewMatchers.withText("4")))
    }
}