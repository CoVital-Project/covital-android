package org.covital.measurements.presentation

import android.content.Context
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.utils.Navigator
import org.covital.common.presentation.utils.SingleLiveEvent

class MeasurementsViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val measureFinishedEvent = SingleLiveEvent<Unit>()
    val resultFinishedEvent = SingleLiveEvent<Unit>()

    var measuredValue: Int? = null

    fun onButtonClicked(context: Context) {
        navigator.navigateOxygenSaturationScreen(context)
    }

    fun onMeasureFinished(value: Int) {
        measuredValue = value
        measureFinishedEvent.call()
    }
    fun onResultFinished() = resultFinishedEvent.call()
    fun onSendData() = sendData()

    private fun sendData() {

    }

}