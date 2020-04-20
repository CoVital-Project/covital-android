package org.covital.account.domain

import org.covital.common.domain.entities.User

data class AccountViewState(
    val user: User,
    val overview: List<OverviewItem>,
    val diagnoses: List<DiagnosisItem>
)
