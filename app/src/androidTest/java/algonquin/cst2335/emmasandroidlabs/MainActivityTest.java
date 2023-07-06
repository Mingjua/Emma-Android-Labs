package algonquin.cst2335.emmasandroidlabs;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This test method tests the functionality of finding a missing lowercase letter in a password.
     * It does this by first finding the editText view with id R.id.editTextTextPassword and typing "PASS@12" into it.
     * Then it finds the button with id R.id.button and clicks it.
     * Finally, it finds the textView with id R.id.textView and checks that its text matches "You shall not pass".
     */
    @Test
    public void testFindMissingLowerCase() {
        // finding the editText view with id R.id.editText;
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextPassword)); //findViewById

        // perform typing "PASS@12" into that view, then close the keyboard
        appCompatEditText.perform(replaceText("PASS@12"), closeSoftKeyboard());

        // find the button with id R.id.button
        ViewInteraction materialButton = onView(withId(R.id.button));

        // perform clicking that button
        materialButton.perform(click());

        // find the textView with id R.id.textView
        ViewInteraction textView = onView(withId(R.id.textView));

        //check that its text matches "You shall not pass!"
        textView.check(matches(withText("You shall not pass")));
    }

    /**
     * This test method tests the functionality of finding a missing uppercase letter in a password.
     * It does this by first finding the editText view with id R.id.editTextTextPassword and typing "password123#$*" into it.
     * Then it finds the button with id R.id.button and clicks it.
     * Finally, it finds the textView with id R.id.textView and checks that its text matches "You shall not pass".
     */
    @Test
    public void testFindMissingUpperCase(){
        //finds the EditText
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextPassword));

        //perform: type in "password123#$*"
        appCompatEditText.perform(replaceText("password123#$*"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));

        //perform: click the button
        materialButton.perform(click());

        //find the TextView
        ViewInteraction textView = onView(withId(R.id.textView));

        //check that the text matches "You shall not pass!
        textView.check(matches(withText("You shall not pass")));

    }

    /**
     * This test method tests the functionality of finding a missing number in a password.
     * It does this by first finding the editText view with id R.id.editTextTextPassword and typing "PASS@pass" into it.
     * Then it finds the button with id R.id.button and clicks it.
     * Finally, it finds the textView with id R.id.textView and checks that its text matches "You shall not pass".
     */
    @Test
    public void testFindMissingNumber() {
        // finding the editText view with id R.id.editText;
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextPassword)); //findViewById

        // perform typing "PASS@pass" into that view, then close the keyboard
        appCompatEditText.perform(replaceText("PASS@pass"), closeSoftKeyboard());

        // find the button with id R.id.button
        ViewInteraction materialButton = onView(withId(R.id.button));

        // perform clicking that button
        materialButton.perform(click());

        // find the textView with id R.id.textView
        ViewInteraction textView = onView(withId(R.id.textView));

        //check that its text matches "You shall not pass!"
        textView.check(matches(withText("You shall not pass")));
    }

    /**
     * This test method tests the functionality of finding a missing special character in a password.
     * It does this by first finding the editText view with id R.id.editTextTextPassword and typing "PASSpass123" into it.
     * Then it finds the button with id R.id.button and clicks it.
     * Finally, it finds the textView with id R.id.textView and checks that its text matches "You shall not pass".
     */
    @Test
    public void testFindMissingSpecialCharacter() {
        // finding the editText view with id R.id.editText;
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextPassword)); //findViewById

        // perform typing "PASSpass123" into that view, then close the keyboard
        appCompatEditText.perform(replaceText("PASSpass123"), closeSoftKeyboard());

        // find the button with id R.id.button
        ViewInteraction materialButton = onView(withId(R.id.button));

        // perform clicking that button
        materialButton.perform(click());

        // find the textView with id R.id.textView
        ViewInteraction textView = onView(withId(R.id.textView));

        //check that its text matches "You shall not pass!"
        textView.check(matches(withText("You shall not pass")));
    }

    /**
     * This test method tests the functionality of matching all the password requirement.
     * It does this by first finding the editText view with id R.id.editTextTextPassword and typing "Password123#$*" into it.
     * Then it finds the button with id R.id.button and clicks it.
     * Finally, it finds the textView with id R.id.textView and checks that its text matches "Your password meets the requirements".
     */
    @Test
    public void testMatchingAllRequirement(){
        //finds the EditText
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextPassword));

        //perform: type in "password123#$*"
        appCompatEditText.perform(replaceText("Password@123"), closeSoftKeyboard());

        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));

        //perform: click the button
        materialButton.perform(click());

        //find the TextView
        ViewInteraction textView = onView(withId(R.id.textView));

        //check that the text matches "You shall not pass!
        textView.check(matches(withText("Your password meets the requirment! ")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
