package org.covital.login.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator

class LoginViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked() {
        navigator.goTo(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
    }
}
