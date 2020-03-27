package org.covital.measurements.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.covital.common.presentation.BaseViewModel

class MeasureViewModel(
    private val sharedViewModel: MeasurementsViewModel
) : BaseViewModel() {

    private val _recording = MutableLiveData(false)
    val recording: LiveData<Boolean> get() = _recording
    val buttonEnabled = Transformations.map(_recording) { !it }

    fun onButtonClick() {
        viewModelScope.launch {
            _recording.value = true
            delay(1000)
            _recording.value = false
            sharedViewModel.onMeasureFinished(45)
        }
    }
}