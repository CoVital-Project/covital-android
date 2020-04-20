package org.covital.settings.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.navigation.Navigator

class SettingsViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onBackTapped() {
        navigator.back()
    }

    fun onChangeEmailTapped() {
        // TODO: navigator.goTo(ChangeEmail)
    }

    fun onChangePasswordTapped() {
        // TODO: navigator.goTo(ChangePassword)
    }

    fun onFeedbackTapped() {
        navigator.goTo(SettingsFragmentDirections.actionSettingsFragmentToFeedbackFragment())
    }

    fun onAboutTapped() {
        // TODO: open web link to CoVital About Page
    }

    fun onHelpTapped() {
        // TODO: open web link to CoVital Help Page
    }

    fun onLogOutTapped() {
        /**
         * TODO: Start LogOutJob
         *       Navigate to Login on success
         *       Show toast on failure
         */

        navigator.goTo(SettingsFragmentDirections.actionToLoginFragment())
    }
}
