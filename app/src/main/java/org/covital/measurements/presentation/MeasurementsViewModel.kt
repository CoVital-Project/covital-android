package org.covital.measurements.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.utils.Navigator
import org.covital.common.presentation.utils.SingleLiveEvent

class MeasurementsViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val measureFinishedEvent = SingleLiveEvent<Unit>()
    val resultFinishedEvent = SingleLiveEvent<Unit>()

    var measuredValue: Int? = null

    private val _hasCameraPermission = MutableLiveData(false)
    val hasCameraPermission: LiveData<Boolean> get() = _hasCameraPermission

    fun onButtonClicked(context: Context) {
        navigator.navigateOxygenSaturationScreen(context)
    }

    fun onCameraPermissionGranted() {
        _hasCameraPermission.value = true
    }

    fun onMeasureFinished(o2: Int) {
        measuredValue = o2
        measureFinishedEvent.call()
    }
    fun onResultFinished() = resultFinishedEvent.call()
    fun onSendData() = sendData()

    private fun sendData() {

    }

}