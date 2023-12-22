package com.dicoding.carvalappandroid.ui.onboarding

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.carvalappandroid.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class BoardingActivityTest{
    @Before
    fun setup(){
        ActivityScenario.launch(BoardingActivity::class.java)
    }

    @Test
    fun validateLogin(){
        Espresso.onView(withId(R.id.logoMenu1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.btn_login)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.email_login)).perform(typeText("billy.franko994@gmail.com"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.password_login)).perform(typeText("Billyf123"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.login_Button)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(withId(R.id.btn_verify_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.btn_verify_login)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.card_form2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}