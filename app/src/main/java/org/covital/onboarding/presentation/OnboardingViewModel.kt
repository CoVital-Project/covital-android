package org.covital.onboarding.presentation

import android.widget.DatePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.covital.common.presentation.BaseViewModel
import org.covital.common.presentation.Navigator
import org.threeten.bp.Instant
import java.util.*

class OnboardingViewModel(val navigator: Navigator): BaseViewModel() {
    private val now = Instant.now()
    private val _datepicker = MutableLiveData(false)
    val datepicker: LiveData<Boolean> = _datepicker
    private val _dateOfBirth = MutableLiveData(now)
    val dateOfBirth: LiveData<Instant> = _dateOfBirth
    val dateOfBirthString: LiveData<String> = Transformations.map(_dateOfBirth) {date ->
        if (date.isBefore(now)) {
            date.toString()
        } else {
            "Select Your Date of Birth"
        }
    }


    fun onNextTapped(){
        navigator.goTo(OnboardingFragmentDirections.actionOnboardingFragmentToDashboardFragment())
    }
    fun onBackTapped(){
        navigator.back()
    }
    fun  onBirthDateDropDownTapped(){
        _datepicker.postValue(true)
    }
    fun onBirthDateSelected(view: DatePicker) {
        _dateOfBirth.postValue(parseDate(view.year, view.month, view.dayOfMonth))
    }
    /**
    * Creates a Calendar instance and sets the fields for year, month,
    * and day without retaining any other information.
    *
    * @param year the value used to set the [Year] field.
    * @param month the value used to set the [Month] field.
    *  Month value is 0-based. e.g., 0 for January.
    * @param day the value used to set the [MonthDay] field.
    */
    fun parseDate(year: Int, month: Int, day: Int): Instant {
        if (month < 0) throw IllegalArgumentException("Month must be a positive number or zero")
        if (month >= 12) throw IllegalArgumentException("Month must be less than 12")
        if (day <= 0) throw IllegalArgumentException("Day must be a positive number")
        if (day > 31) throw IllegalArgumentException("Day must be less than or equal to 31")
        val millis = Calendar.getInstance().apply {
            clear()
            set(year, month, day)
            timeZone = TimeZone.getTimeZone("UTC")
        }.timeInMillis
        return Instant.ofEpochMilli(millis)
    }
}
