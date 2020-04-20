package org.covital.feedback.presentation

import org.covital.common.presentation.BaseViewModel
import org.covital.common.navigation.Navigator

class FeedbackViewModel(
    private val navigator: Navigator
) : BaseViewModel() {

    val feedback: String = ""

    fun onBackTapped() {
        navigator.back()
    }

    fun onRatingTapped(rating: Int) {

    }
}
