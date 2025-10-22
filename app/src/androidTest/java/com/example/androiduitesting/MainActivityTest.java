package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity() {
        // Click on Add City button
        onView(withId(R.id.button_add)).perform(click());

        // Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));

        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if text "Edmonton" is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());

        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView(){
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if in the Adapter view (given id of that adapter view) there is a data
        // (which is an instance of String) located at position zero.
        // If this data matches the text we provided then Voila! Our test should pass
        // You can also use anything() in place of is(instanceOf(String.class))
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list
        )).atPosition(0).check(matches((withText("Edmonton"))));
    }

    @Test
    public void testActivitySwitchesToCityLayout() {
        // Arrange: add a city name to the list
        scenario.getScenario().onActivity(activity -> {
            activity.dataList.add("Edmonton");
            activity.cityAdapter.notifyDataSetChanged();
        });

        // Act: click the first item in the list
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Assert: check that the new layout is loaded
        // and the city name text is displayed
        onView(withId(R.id.cityNameText))
                .check(matches(isDisplayed()));

        // Optionally check the text
        onView(withId(R.id.cityNameText))
                .check(matches(withText("Edmonton")));
    }

    @Test
    public void testCityNameConsistency() {
        // Arrange
        final String testCity = "Tokyo";
        scenario.getScenario().onActivity(activity -> {
            activity.dataList.add(testCity);
            activity.cityAdapter.notifyDataSetChanged();
        });

        // Act
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Assert
        // Verify that the correct city name is displayed in the city detail layout
        onView(withId(R.id.cityNameText))
                .check(matches(isDisplayed()))
                .check(matches(withText(testCity)));
    }

    @Test
    public void testBackButtonReturnsToMainLayout() {
        // Arrange
        final String testCity = "Berlin";
        scenario.getScenario().onActivity(activity -> {
            activity.dataList.add(testCity);
            activity.cityAdapter.notifyDataSetChanged();
        });

        // Act
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        onView(withId(R.id.backButton)).perform(click());

        // Assert
        // Check that the ListView is visible again (meaning we’re back to the main layout)
        onView(withId(R.id.city_list))
                .check(matches(isDisplayed()));

        // Also ensure the previously added city still exists in the list
        onData(anything()).inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches(withText(testCity)));
    }
}
