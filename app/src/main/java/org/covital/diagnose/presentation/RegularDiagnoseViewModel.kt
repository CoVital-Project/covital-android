package org.covital.diagnose.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator

class RegularDiagnoseViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    fun onButtonClicked() {
        navigator.goTo(RegularDiagnoseFragmentDirections.actionRegularDiagnoseFragmentToMeasureGraph())
    }
}
