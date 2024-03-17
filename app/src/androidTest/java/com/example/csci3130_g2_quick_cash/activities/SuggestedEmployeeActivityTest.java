package com.example.csci3130_g2_quick_cash.activities;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
<<<<<<<< HEAD:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/PreferredPostingsActivityTest.java
========

import android.content.Intent;
>>>>>>>> 2db533c1b797b495536137f7060ca8c45ed51e22:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/SuggestedEmployeeActivityTest.java

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.csci3130_g2_quick_cash.R;

<<<<<<<< HEAD:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/PreferredPostingsActivityTest.java

========
>>>>>>>> 2db533c1b797b495536137f7060ca8c45ed51e22:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/SuggestedEmployeeActivityTest.java
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

<<<<<<<< HEAD:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/PreferredPostingsActivityTest.java
/**
 * Preferred Postings UI Espresso test cases.
 *
 * @author Arman Singh Sidhu
 */
public class PreferredPostingsActivityTest {
    @Rule
    public ActivityScenarioRule<PreferredPostingsActivity> activityTestRule = new ActivityScenarioRule<>(PreferredPostingsActivity.class);
========
public class SuggestedEmployeeActivityTest {
    @Rule
    public ActivityScenarioRule<SuggestedEmployeeActivity> activityTestRule = new ActivityScenarioRule<>(SuggestedEmployeeActivity.class);
>>>>>>>> 2db533c1b797b495536137f7060ca8c45ed51e22:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/SuggestedEmployeeActivityTest.java

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void checkUIElementsExist() {
<<<<<<<< HEAD:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/PreferredPostingsActivityTest.java
        onView(ViewMatchers.withId(R.id.backBut)).check(matches(isDisplayed()));
        onView(withId(R.id.filteredPosts)).check(matches(isDisplayed()));
    }

    @Test
    public void checkBackButton() {
        closeSoftKeyboard();
        onView(withId(R.id.backBut)).perform(click());
        intended(hasComponent(WelcomeActivity.class.getName()));
========
        onView(ViewMatchers.withId(R.id.closeSuggestions)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.suggestionsEmployeeTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.employee_recycler_view)).check(matches(isDisplayed()));
>>>>>>>> 2db533c1b797b495536137f7060ca8c45ed51e22:app/src/androidTest/java/com/example/csci3130_g2_quick_cash/activities/SuggestedEmployeeActivityTest.java
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
