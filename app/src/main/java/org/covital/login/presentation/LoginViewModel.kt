package org.covital.login.presentation

import android.content.Context
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.utils.Navigator

class LoginViewModel(
    val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked(context: Context) {
        navigator.navigateToDashboardScreen(context)
    }

}