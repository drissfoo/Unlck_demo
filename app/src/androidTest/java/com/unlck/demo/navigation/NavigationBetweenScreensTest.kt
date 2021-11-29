package com.unlck.demo.navigation

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.unlck.demo.R
import com.unlck.demo.ui.listalbum.ListAlbumFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class NavigationBetweenScreensTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testNavigateToDetail() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        launchFragmentInHiltContainer<ListAlbumFragment> {
            navController.setGraph(R.navigation.main_nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(requireView(), navController)
            recyclerViewVisible?.registerIdleTransitionCallback {
                onView(withId(R.id.album_recycler_view))
                    .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
                // Check navigate successfully
                assertEquals(R.id.detailAlbumFragment, navController.currentDestination?.id)
            }
        }
    }
}