package org.covital.dashboard.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.navigation.Navigator

class DashboardViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onDiagnoseTapped() {
        navigator.goTo(DashboardFragmentDirections.actionDashboardFragmentToRegularDiagnoseFragment())
    }

    fun onAccountTapped() {
        navigator.goTo(DashboardFragmentDirections.actionDashboardFragmentToAccountFragment())
    }
}
