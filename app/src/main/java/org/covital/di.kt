package org.covital

import org.covital.common.data.datasource.ItemsDataSource
import org.covital.common.data.datasource.remote.ApiServiceFactory
import org.covital.common.data.datasource.remote.ItemsRemoteDataSource
import org.covital.common.data.repository.ItemsDataRepository
import org.covital.common.domain.ItemsRepository
import org.covital.common.presentation.utils.Navigator
import org.covital.dashboard.presentation.DashboardActivity
import org.covital.dashboard.presentation.DashboardViewModel
import org.covital.diagnose.presentation.RegularDiagnoseActivity
import org.covital.diagnose.presentation.RegularDiagnoseViewModel
import org.covital.login.presentation.LoginActivity
import org.covital.login.presentation.LoginViewModel
import org.covital.measurements.presentation.MeasurementsActivity
import org.covital.measurements.presentation.MeasurementsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun App.initKoin() {
    startKoin {
        androidLogger(Level.DEBUG)
        androidContext(this@initKoin)
        modules(
            listOf(
                appModule,
                dataModule,
                scopedModules
            )
        )
    }
}

private val appModule = module {
    single { ApiServiceFactory.create() }
    single { Navigator() }
}

private val dataModule = module {
    factory<ItemsRepository> { ItemsDataRepository(get()) }
    factory<ItemsDataSource> { ItemsRemoteDataSource(get()) }
}

private val scopedModules = module {
    scope(named<LoginActivity>()) {
        viewModel { LoginViewModel(get()) }
    }

    scope(named<DashboardActivity>()) {
        viewModel { DashboardViewModel(get()) }
    }

    scope(named<RegularDiagnoseActivity>()) {
        viewModel { RegularDiagnoseViewModel(get()) }
    }

    scope(named<MeasurementsActivity>()) {
        viewModel { MeasurementsViewModel(get()) }
    }
}
