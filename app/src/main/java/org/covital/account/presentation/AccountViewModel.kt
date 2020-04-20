package org.covital.account.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.covital.account.domain.DiagnosisItem
import org.covital.account.domain.OverviewItem
import org.covital.account.domain.RecordingItem
import org.covital.account.usecase.AccountInteractor
import org.covital.common.domain.Either
import org.covital.common.domain.entities.User
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator

class AccountViewModel(
    private val navigator: Navigator,
    private val interactor: AccountInteractor
) : BaseViewModel() {

    val userAndOverview: LiveData<Either<Pair<User, List<OverviewItem>>>> = liveData {
        emit(interactor.loadUser())
    }

    val history: LiveData<Either<List<RecordingItem>>> = liveData {
        emit(interactor.loadRecentHistory())
    }

    val diagnoses: LiveData<List<DiagnosisItem>> = liveData {
        emit(interactor.loadDiagnoses())
    }

    fun onSettingsTapped() {
        navigator.goTo(AccountFragmentDirections.actionAccountFragmentToSettingsFragment())
    }

    fun onBackTapped() {
        navigator.back()
    }

    fun onOverviewItemTapped(item: OverviewItem) {
        navigator.goTo(TODO("Action does not yet exist"))
    }

    fun onDiagnosisItemTapped(item: DiagnosisItem) {
        viewModelScope.launch {
            interactor.persistDiagnosisChanged(item)
        }
    }

    fun onHistoryItemTapped(item: RecordingItem) {
        when (item) {
            is RecordingItem.AddNewMeasurement -> navigator.goTo(TODO("Action does not yet exist"))
            is RecordingItem.Recording -> navigator.goTo(TODO("Action does not yet exist"))
        }
    }
}
