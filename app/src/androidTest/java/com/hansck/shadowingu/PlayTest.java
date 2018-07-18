package com.hansck.shadowingu;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hansck.shadowingu.screen.login.LoginActivity;
import com.hansck.shadowingu.screen.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Hans CK on 18-Jul-18.
 */
@RunWith(AndroidJUnit4.class)
public class PlayTest {

	@Rule
	public ActivityTestRule<MainActivity> activityTestRule =
		new ActivityTestRule<>(MainActivity.class, true, true);

	@Rule
	public ActivityTestRule<LoginActivity> loginActivityTestRule =
		new ActivityTestRule<>(LoginActivity.class, true, true);

	@Test
	public void playGameAndWin() {
		onView(allOf(isDisplayed(), withId(R.id.stageList)))
			.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

		for (int i = 0; i < 10; i++) {
			onView(withId(R.id.btnRecording)).perform(click());
		}

		onView(withId(R.id.btnContinue)).perform(click());
	}

	@Test
	public void playGameAndLose() {
		onView(allOf(isDisplayed(), withId(R.id.stageList)))
			.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

		for (int i = 0; i < 3; i++) {
			onView(withId(R.id.btnVoice)).perform(click());
		}

		onView(withId(R.id.btnContinue)).perform(click());
	}
}
