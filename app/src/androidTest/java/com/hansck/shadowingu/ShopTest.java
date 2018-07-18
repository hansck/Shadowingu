package com.hansck.shadowingu;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.hansck.shadowingu.screen.login.LoginActivity;
import com.hansck.shadowingu.screen.main.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Hans CK on 18-Jul-18.
 */
@RunWith(AndroidJUnit4.class)
public class ShopTest {

	@Rule
	public ActivityTestRule<MainActivity> activityTestRule =
		new ActivityTestRule<>(MainActivity.class, true, true);

	@Rule
	public ActivityTestRule<LoginActivity> loginActivityTestRule =
		new ActivityTestRule<>(LoginActivity.class, true, true);

	@Test
	public void buyAvatar() {

		onView(allOf(isDisplayed(), withText("Shop"))).perform(click());

		onView(allOf(isDisplayed(), withId(R.id.avatarList)))
			.perform(RecyclerViewActions.actionOnItemAtPosition(2, MyViewAction.clickChildViewWithId(R.id.btnBuy)));

		onView(withId(R.id.gem)).check(matches(withText("4")));
	}
}

class MyViewAction {

	public static ViewAction clickChildViewWithId(final int id) {
		return new ViewAction() {
			@Override
			public Matcher<View> getConstraints() {
				return null;
			}

			@Override
			public String getDescription() {
				return "Click on a child view with specified id.";
			}

			@Override
			public void perform(UiController uiController, View view) {
				View v = view.findViewById(id);
				v.performClick();
			}
		};
	}

}
