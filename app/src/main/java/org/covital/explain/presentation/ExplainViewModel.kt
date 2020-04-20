package org.covital.explain.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.covital.R
import org.covital.common.permissions.Permission
import org.covital.common.presentation.BaseViewModel
import org.covital.common.navigation.Navigator
import org.covital.common.permissions.Approver

class ExplainViewModel(
    private val navigator: Navigator,
    val approver: Approver
) : BaseViewModel() {

    private val states = listOf(
        R.id.getting_started_a,
        R.id.getting_started_b,
        R.id.getting_started_c)

    private val firstState = states.first()

    private val _motionState = MutableLiveData(firstState to firstState)
    val transitions: LiveData<Pair<Int, Int>> = _motionState

    private fun currentState(): Int? {
        return _motionState.value?.second?.run {
            if (this in states) {
                this
            } else {
                null
            }
        }
    }

    fun onBackTapped(): Boolean {
        when (val current = currentState()) {
            states[0] -> navigator.back()
            states[1] -> _motionState.postValue(current to states[0])
            states[2] -> _motionState.postValue(current to states[1])
        }

        return true
    }

    fun onNextTapped() {
        when (val current = currentState()) {
            states[0] -> _motionState.postValue(current to states[1])
            states[1] -> _motionState.postValue(current to states[2])
            states[2] -> approver.request(Permission.Camera)
        }
    }

    fun onSkipTapped() {
        val current = currentState()
        if (current == states[1]) {
            _motionState.postValue(current to states.last())
        }
    }

    fun onNoThanksTapped() {
        navigator.goTo(ExplainFragmentDirections.actionExplainFragmentToDashboardFragment())
    }

    fun onCameraPermissionAccepted() {
        navigator.goTo(ExplainFragmentDirections.actionExplainFragmentToDashboardFragment())
    }

    fun onTransitionCompleted(from: Int, to: Int) {
        if (from in states && to in states) {
            _motionState.postValue(from to to)
        }
    }
}
