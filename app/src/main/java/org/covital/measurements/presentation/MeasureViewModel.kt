package org.covital.measurements.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.covital.R
import org.covital.common.presentation.BaseViewModel

class MeasureViewModel(
    private val sharedViewModel: MeasurementsViewModel
) : BaseViewModel() {
    private var isFirstRecording = true

    private val _recording = MutableLiveData(false)
    val recording: LiveData<Boolean> = _recording
    val buttonEnabled = Transformations.map(_recording) { !it }
    val showError = Transformations.map(_recording) { isRecording ->
        val showError = !isFirstRecording && !isRecording
        if (isRecording) isFirstRecording = false
        showError
    }

    val buttonText = Transformations.map(sharedViewModel.hasCameraPermission) {
        if (it) R.string.measure_screen_button else R.string.measure_screen_button_permission
    }
    val titleText = Transformations.map(sharedViewModel.hasCameraPermission) {
        if (it) R.string.measure_screen_title else R.string.measure_screen_title_permission
    }
    val descriptionText = Transformations.map(sharedViewModel.hasCameraPermission) {
        if (it) R.string.measure_screen_description
        else R.string.measure_screen_description_permission
    }

    fun onButtonClick() {
        if (sharedViewModel.hasCameraPermission.value == true) {
            _recording.value = true
        }
    }

    fun onAnalyzerResult(o2: Int) {
        if (o2 <= 0) {
            _recording.value = false
        } else {
            _recording.value = false
            isFirstRecording = true
            sharedViewModel.onMeasureFinished(o2)
        }
    }

    fun resetState() {
        _recording.value = false
        isFirstRecording = true
    }

}
