package org.covital.dashboard.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator

class DashboardViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked() {
        navigator.goTo(DashboardFragmentDirections.actionDashboardFragmentToRegularDiagnoseFragment())
    }
}
