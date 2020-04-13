package org.covital

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import org.covital.common.data.datasource.ItemsDataSource
import org.covital.common.data.datasource.MoshiFactory
import org.covital.common.data.datasource.remote.ApiServiceFactory
import org.covital.common.data.datasource.remote.ItemsRemoteDataSource
import org.covital.common.data.repository.ItemsDataRepository
import org.covital.common.domain.ItemsRepository
import org.covital.common.presentation.MainActivity
import org.covital.common.presentation.MainViewModel
import org.covital.common.presentation.Navigator
import org.covital.dashboard.presentation.DashboardFragment
import org.covital.dashboard.presentation.DashboardViewModel
import org.covital.diagnose.presentation.RegularDiagnoseFragment
import org.covital.diagnose.presentation.RegularDiagnoseViewModel
import org.covital.login.presentation.LoginFragment
import org.covital.login.presentation.LoginViewModel
import org.covital.measurements.presentation.MeasureFragment
import org.covital.measurements.presentation.MeasureViewModel
import org.covital.measurements.presentation.MeasurementsViewModel
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
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
    single { MoshiFactory.create() }
    single { ApiServiceFactory.create(get()) }
    single { Navigator() }
    single { MeasurementsViewModel(get()) }
}

private val dataModule = module {
    factory<ItemsRepository> { ItemsDataRepository(get()) }
    factory<ItemsDataSource> { ItemsRemoteDataSource(get()) }
}

private val scopedModules = module {
    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get()) }
    }

    scope(named<DashboardFragment>()) {
        viewModel { DashboardViewModel(get()) }
    }

    scope(named<RegularDiagnoseFragment>()) {
        viewModel { RegularDiagnoseViewModel(get()) }
    }

    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
    }

    scope(named<MeasureFragment>()) {
        viewModel { (sharedViewModel: MeasurementsViewModel) -> MeasureViewModel(sharedViewModel) }
    }
}

/**
 * We added this because Koin doesn't yet have this extension function in order to handle
 * Navigation Component's concept of navigation graph scoped ViewModels.
 *
 * TODO: Once this issue is resolved lets delete this function and use the standard one
 *     https://github.com/InsertKoinIO/koin/issues/442
 */
inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
    @IdRes navGraphId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val store = NavHostFragment.findNavController(this).getViewModelStoreOwner(navGraphId).viewModelStore
    getKoin().getViewModel(ViewModelParameter(VM::class, qualifier, parameters, null, store, null))
}
