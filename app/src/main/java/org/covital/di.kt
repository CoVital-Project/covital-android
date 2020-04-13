package org.covital

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import org.covital.account.presentation.AccountFragment
import org.covital.account.presentation.AccountViewModel
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import org.covital.common.data.datasource.MoshiFactory
import org.covital.common.data.datasource.PrefsFactory
import org.covital.common.data.datasource.remote.ApiServiceFactory
import org.covital.common.data.datasource.remote.ItemsGateway
import org.covital.common.data.repository.ItemsRepository
import org.covital.common.data.repository.DatabaseFactory
import org.covital.common.logging.Acorn
import org.covital.common.presentation.MainActivity
import org.covital.common.presentation.MainViewModel
import org.covital.common.presentation.Navigator
import org.covital.dashboard.presentation.DashboardFragment
import org.covital.dashboard.presentation.DashboardViewModel
import org.covital.diagnose.presentation.RegularDiagnoseFragment
import org.covital.diagnose.presentation.RegularDiagnoseViewModel
import org.covital.feedback.presentation.FeedbackFragment
import org.covital.feedback.presentation.FeedbackViewModel
import org.covital.login.presentation.LoginFragment
import org.covital.login.presentation.LoginViewModel
import org.covital.measurements.presentation.MeasureFragment
import org.covital.measurements.presentation.MeasureViewModel
import org.covital.measurements.presentation.MeasurementsViewModel
import org.covital.settings.presentation.SettingsFragment
import org.covital.settings.presentation.SettingsViewModel
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun App.initKoin() {
    startKoin {
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
    single { ApiServiceFactory.create(get(), get()) }
    single { Navigator() }
    single { MeasurementsViewModel(get()) }
    single { PrefsFactory.create(androidContext(), get()) }
    single { NetworkFlipperPlugin() }
    single { DatabaseFactory.create(get()) }
    single { Acorn(logcatEnabled = true) }
}

private val dataModule = module {
    factory { ItemsRepository(get()) }
    factory { ItemsGateway(get()) }
}

private val scopedModules = module {
    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get()) }
    }

    scope(named<DashboardFragment>()) {
        viewModel { DashboardViewModel(get()) }
    }

    scope(named<AccountFragment>()) {
        viewModel { AccountViewModel(get()) }
    }

    scope(named<SettingsFragment>()) {
        viewModel { SettingsViewModel(get()) }
    }

    scope(named<FeedbackFragment>()) {
        viewModel { FeedbackViewModel(get()) }
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
