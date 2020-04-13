package org.covital.account.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator

class AccountViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onSettingsTapped() {
        navigator.goTo(AccountFragmentDirections.actionAccountFragmentToSettingsFragment())
    }

    fun onBackTapped() {
        navigator.back()
    }
}
