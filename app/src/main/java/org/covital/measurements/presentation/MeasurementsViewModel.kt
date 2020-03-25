package org.covital.measurements.presentation

import android.content.Context
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.utils.Navigator

class MeasurementsViewModel(
    val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked(context: Context) {
        navigator.navigateOxygenSaturationScreen(context)
    }

}