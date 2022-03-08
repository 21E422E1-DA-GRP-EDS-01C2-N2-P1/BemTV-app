package br.infnet.bemtvi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import br.infnet.bemtvi.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class UiTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loginOnAccount(){
        //onView(ViewMatchers.withId(R.))
        val entrarBtn = onView(ViewMatchers.withText("entrar"))
        val result = entrarBtn.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            entrarBtn.check(ViewAssertions.matches(ViewMatchers.isClickable()))
        if(result.equals(true)){

        }
        onView(ViewMatchers.withText("entrar")).perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.editTextTextEmailAddress)).perform(ViewActions.typeText("ga@bris.com"))
        onView(ViewMatchers.withId(R.id.editTextTextPassword)).perform(ViewActions.typeText("123456"))
        onView(ViewMatchers.withText("login")).perform(ViewActions.click())
        val d=0

    }
}