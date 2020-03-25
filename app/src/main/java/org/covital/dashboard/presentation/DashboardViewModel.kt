package org.covital.dashboard.presentation

import android.content.Context
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.utils.Navigator

class DashboardViewModel(
    val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked(context: Context) {
        navigator.navigateToRegularDiagnose(context)
    }
}
