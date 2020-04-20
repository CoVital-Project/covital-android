package org.covital.common.presentation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.covital.R
import org.covital.common.extensions.observe
import org.covital.common.presentation.navigation.Route
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findNavController(this, R.id.nav_host)

        observe(viewModel.getLiveRouting()) { route ->
            when (route) {
                is Route.Forward -> navigateTo(route.direction)
                else -> navigateBack()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        try {
            findNavController(this, R.id.nav_host).apply {
                Timber.d("Resuming app into ${currentDestination.getScreenName()}")
                setAnalyticsScreenName(currentDestination)
            }
        } catch (ex: Throwable) {

        }

        // TODO: Add subscription for navigation events


//        vamp.getToastsUpdates()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                showToast(it.first, it.second)
//            }
//            .disposeOn(this, Lifecycle.Event.ON_PAUSE)
    }

    override fun onBackPressed() {

        val currentFragment = getCurrentFragment()

        findNavController(this, R.id.nav_host).apply {

            val processingBack = currentFragment?.onBackPressed() ?: false
            if (!processingBack) {
                Timber.d("Backing out of ${currentDestination.getScreenName()}")
                super.onBackPressed()
            } else {
                Timber.d("Remaining in ${currentDestination.getScreenName()}")
            }

            setAnalyticsScreenName(currentDestination)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getCurrentFragment()?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()

        // TODO: If Keyboard is open close it
    }

    private fun getCurrentFragment(): BaseFragment<*>? {
        return try {
            nav_host?.childFragmentManager?.fragments?.firstOrNull() as? BaseFragment<*>
        } catch (ex: Throwable) {
            null
        }
    }

    /**
     * Navigates to the specified destination using
     * Navigation Component or starting an activity.
     */
    private fun navigateTo(route : NavDirections) {
        if (isFinishing) return

        try {
            findNavController(this, R.id.nav_host).apply {
                val from = currentDestination.getScreenName()
                navigate(route)
                setAnalyticsScreenName(currentDestination)
                Timber.d("Routing from $from -> ${currentDestination.getScreenName()}")
            }
        } catch (ex: Exception) {
            /**
             * Could not perform navigation, possibly the current fragment
             * does not have a route to the destination
             */
        }
    }

    private fun navigateBack() {
        findNavController(this, R.id.nav_host).apply {
            super.onBackPressed()
            Timber.d("Backing out of ${currentDestination.getScreenName()}")
            setAnalyticsScreenName(currentDestination)
        }
    }

    private fun setAnalyticsScreenName(destination: NavDestination?) {

        val screenName = destination?.getScreenName()

        try {
            // TODO: Add analytics to set screen names
            // FirebaseAnalytics.getInstance(baseContext).setCurrentScreen(this, screenName, javaClass.simpleName)
        } catch (ex: Throwable) {

        }
    }

    private fun NavDestination?.getScreenName(): String {
        return this?.label?.toString() ?: "Unknown"
    }

    fun setStatusBarDarkness(opacity: Float, progress: Float = 1f) {
        window.statusBarColor = Color.argb((255 * opacity * progress).toInt(), 0, 0, 0)
    }

}
