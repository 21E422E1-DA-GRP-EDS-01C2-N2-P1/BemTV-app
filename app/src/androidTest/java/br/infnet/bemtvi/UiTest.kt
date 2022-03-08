package br.infnet.bemtvi

import androidx.test.espresso.Espresso.onView
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
    fun findLoginButtonByText(){
        //onView(ViewMatchers.withId(R.))
        onView(ViewMatchers.withText("entrar"))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
        val d=0

    }
}