package org.covital.diagnose.presentation

import android.content.Context
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.utils.Navigator

class RegularDiagnoseViewModel(
    val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked(context: Context) {
        navigator.navigateToMeasurements(context)
    }
}
