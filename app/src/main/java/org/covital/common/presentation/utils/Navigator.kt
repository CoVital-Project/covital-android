package org.covital.common.presentation.utils

import android.content.Context
import android.content.Intent
import org.covital.dashboard.presentation.DashboardActivity
import org.covital.diagnose.presentation.RegularDiagnoseActivity
import org.covital.measurements.presentation.MeasurementsActivity

class Navigator {

    fun navigateToDashboardScreen(context: Context) {
        val intent = Intent(context, DashboardActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToRegularDiagnose(context: Context) {
        val intent = Intent(context, RegularDiagnoseActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToMeasurements(context: Context) {
        val intent = Intent(context, MeasurementsActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateOxygenSaturationScreen(context: Context) {

    }

}