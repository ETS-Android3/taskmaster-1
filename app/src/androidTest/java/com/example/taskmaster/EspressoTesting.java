package com.example.taskmaster;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
//import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class EspressoTesting {

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void assertHomeUIElements(){
        onView(withText("TaskMaster")).check(matches(isDisplayed()));
        onView(withId(R.id.textView)).check(matches(withText("My Tasks")));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.recycleViewId)).check(matches(isDisplayed()));
        onView(withId(R.id.recycleViewId)).perform(click());
        onView(withId(R.id.recycleViewId)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.textView7)).check(matches(withText("workout")));
//        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
         Espresso.pressBack();

        onView(withId(R.id.addTask)).perform(click());
        onView(withId(R.id.textView2)).check(matches(withText("Add Task")));
        onView(withId(R.id.taskTitleInputId)).perform(replaceText("Walk"), closeSoftKeyboard());
        onView(withId(R.id.taskDescInputId)).perform(replaceText("ONe Houre daily"), closeSoftKeyboard());
        onView(withId(R.id.taskStateInputId)).perform(replaceText("in progress"), closeSoftKeyboard());
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        onView(withId(R.id.allTasks)).perform(click());
        onView(withId(R.id.textView5)).check(matches(withText("All Tasks")));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        onView(withId(R.id.settingsId)).perform(click());
        onView(withId(R.id.userNameInput)).perform(replaceText("Dody"));
        onView(withId(R.id.saveId)).perform(click());
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

//    @Test
//    public void testAddTask(){
//        onView(withId(R.id.addTask)).perform(click());
//        onView(withId(R.id.textView2)).check(matches(withText("Add Task")));
//    }
//
//    @Test
//    public void assertTextChanged() {
//
//        onView(withId(R.id.button7)).perform(click());
//        onView(withId(R.id.editTextTextPersonName3)).perform(typeText("Doaa"), closeSoftKeyboard());
//        onView(withId(R.id.button8)).perform(click());
//        onView(withId(R.id.textView)).check(matches(withText("Doaa's Task")));
//    }
//
//    @Test
//    public void testOpenTaskDetail(){
//
//        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        onView(withId(R.id.textView7)).check(matches(withText("Germany")));
//        onView(withId(R.id.textView8)).check(matches(withText("Review")));
//        onView(withId(R.id.textView10)).check(matches(withText("Do")));
//    }
//
//    @Test
//    public void addTaskAndCheckItInTheList(){
//
//        onView(withId(R.id.button)).perform(click());
//        onView(withId(R.id.editTextTextPersonName2)).perform(typeText("ReactJS"), closeSoftKeyboard());
//        onView(withId(R.id.editTextTextPersonName3)).perform(typeText("Build a new project"), closeSoftKeyboard());
//        onView(withId(R.id.editTextTextPersonName)).perform(typeText("New"), closeSoftKeyboard());
//        onView(withId(R.id.button2)).perform(click());
//        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
//        onView(withId(R.id.textView7)).check(matches(withText("ReactJS")));
//        onView(withId(R.id.textView8)).check(matches(withText("Build a new project")));
//        onView(withId(R.id.textView10)).check(matches(withText("New")));
//    }
}

