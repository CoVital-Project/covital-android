package org.covital.login.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.navigation.Navigator

class LoginViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onCreateAccountTapped() {
        navigator.goTo(LoginFragmentDirections.actionLoginFragmentToOnboardingFragment())
    }

    fun onSignInTapped() {
        navigator.goTo(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
    }

    fun onTermsOfServiceTapped() {
        TODO("Not yet implemented")
    }
}
