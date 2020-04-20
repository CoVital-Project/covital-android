package org.covital.common.presentation.ui


import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.animation.ValueAnimator
import android.graphics.Color
import androidx.core.content.ContextCompat
import org.covital.R

object StatusBar {

    @TargetApi(Build.VERSION_CODES.M)
    fun setStatusIconsDark(window: Window) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun setStatusIconsLight(window: Window) {
        window.decorView.systemUiVisibility = 0
    }

    var anim: ValueAnimator? = null

    /**
     * Given the current window and desired status bar color, determine whether changing
     * the status bar icons is necessary and possible. As a result we want to find out
     * what a feasible color for the status bar could be based on what was requested.
     */
    private fun getFeasibleColor(window: Window, @ColorRes colorRes: Int): Int {

        // Was a white status bar requested?
        val whiteStatusBar = colorRes in setOf(
            R.color.white,
            android.R.color.white,
            R.color.color_primary,
            R.color.gray_170)

        return if (whiteStatusBar) {
            // Since its a white status bar we need to try to make the icons dark.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusIconsDark(window)
                colorRes
            } else {
                /**
                 * We MUST NOT set the status bar white on this version of Android.
                 * This is because we do not have access to change Android status
                 * icon color from anything other than white. In this case we will
                 * default to Hinge black.
                 */
                R.color.color_primary_dark
            }
        } else {
            /**
             * Assume that not white means a dark color and light icons (the default). This means we can
             * use the color we want even if we're not on Android M. We must set the icons to be light if
             * we are on M in case some other request asked for a white status bar on Android M, which
             * would have set the icons to be dark.
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusIconsLight(window)
            }
            colorRes
        }
    }

    fun setColor(window: Window, context: Context, @ColorRes colorRes: Int) {
        anim?.cancel()
        anim = null
        val resolvedColorRes = getFeasibleColor(window, colorRes)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, resolvedColorRes)
    }

    fun animColor(window: Window, context: Context, @ColorRes colorRes: Int, duration: Long = 100, startDelay: Long = 0) {

        val resolvedColorRes = getFeasibleColor(window, colorRes)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val toColor = ContextCompat.getColor(context, resolvedColorRes)

        anim = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener { animation ->
                // Apply blended color to the status bar.
                window.statusBarColor = blend(
                    from = window.statusBarColor,
                    to = toColor,
                    ratio = animation.animatedFraction)
            }

            this.duration = duration
            this.startDelay = startDelay
            start()
        }
    }

    /**
     * Blend some colors together based on a floating point as the ratio of blending
     */
    private fun blend(from: Int, to: Int, ratio: Float): Int {
        val inverseRatio = 1f - ratio

        val r = Color.red(to) * ratio + Color.red(from) * inverseRatio
        val g = Color.green(to) * ratio + Color.green(from) * inverseRatio
        val b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio

        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }
}
