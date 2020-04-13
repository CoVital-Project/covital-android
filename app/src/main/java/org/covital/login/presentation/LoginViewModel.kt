package org.covital.login.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator

class LoginViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onCreateAccountTapped() {
        TODO("Not yet implemented")
    }

    fun onSignInTapped() {
        navigator.goTo(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
    }

    fun onTermsOfServiceTapped() {
        TODO("Not yet implemented")
    }
}
