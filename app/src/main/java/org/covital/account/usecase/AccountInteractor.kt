package org.covital.account.usecase

import org.covital.account.domain.DiagnosisItem
import org.covital.account.domain.OverviewItem
import org.covital.account.domain.RecordingItem
import org.covital.common.data.repository.DiagnosisRepository
import org.covital.common.data.repository.RecordingRepository
import org.covital.common.data.repository.UserRespository
import org.covital.common.data.utils.DateAdapter
import org.covital.common.domain.Either
import org.covital.common.domain.Try
import org.covital.common.domain.entities.Diagnosis
import org.covital.common.domain.entities.User
import org.covital.common.domain.model.HeightSystem
import org.covital.common.domain.model.SkinTone
import org.covital.common.domain.model.WeightSystem
import org.covital.common.domain.remap
import org.threeten.bp.Instant

class AccountInteractor(
    private val diagnosisRepository: DiagnosisRepository,
    private val userRepository: UserRespository,
    private val recordingRepository: RecordingRepository
) {

    suspend fun persistDiagnosisChanged(diagnosisItem: DiagnosisItem) {
        val now = Instant.now()
        diagnosisRepository.persist(Diagnosis(
            diagnosisItem.title,
            "0",
            diagnosisItem.checked,
            diagnosisItem.created
        ))
    }

    suspend fun loadUser(): Either<Pair<User, List<OverviewItem>>> {
        val user = Try { userRepository.getUser() }.or {
            User(
                "",
                "",
                "",
                "someone@example.com",
                DateAdapter().fromJson("1986-11-16T00:00:00+0000"),
                null,
                null,
                WeightSystem.Kilograms,
                null,
                HeightSystem.Centimeters,
                null,
                null,
                null,
                Instant.now(),
                Instant.now(),
                false
            )
        }

        return Either.just(user to organizeOverview(user))
    }

    suspend fun loadDiagnoses(): List<DiagnosisItem> {
        val result = Try {
            diagnosisRepository.getAll()
                .map { DiagnosisItem.fromEntity(it) }
        }.or { emptyList() }

        return defaultDiagnoses().map { default ->
            result.firstOrNull { it.title == default.title } ?: default
        }
    }

    private fun organizeDiagnoses(diagnoses: List<DiagnosisItem>): List<DiagnosisItem> {
        return diagnoses.sortedBy {
            it.title
        }
    }

    private fun defaultDiagnoses(): List<DiagnosisItem> {
        return listOf(
            DiagnosisItem("Chronic Lung Issues"),
            DiagnosisItem("Cancer"),
            DiagnosisItem("Cardiovascular Disease"),
            DiagnosisItem("Autoimmune Disease")
        )
    }

    suspend fun loadRecentHistory(): Either<List<RecordingItem>> {
        return recordingRepository.getRecentHistory()
            .remap { RecordingItem.fromEntity(it) }
            .map { recordingItems -> listOf(RecordingItem.AddNewMeasurement) + recordingItems }
    }

    private fun organizeOverview(user: User): List<OverviewItem> {
        return listOf(
            OverviewItem.DateOfBirth(user.dateOfBirth),
            OverviewItem.Gender(user.gender),
            OverviewItem.Weight(user.weight),
            OverviewItem.Height(user.height),
            OverviewItem.CovidTest(user.covid19TestedResult),
            OverviewItem.Skin(user.skinTone)
        )
    }
}
