package io.github.louistsaitszho.louisshutterstock

import android.app.Application
import io.github.louistsaitszho.louisshutterstock.model.Repository
import io.github.louistsaitszho.louisshutterstock.model.RepositoryImpl
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import timber.log.Timber

/**
 * Application class of this app -> do a bunch of init process
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() { Timber.plant(Timber.DebugTree()) }

    private fun initKoin() { startKoin(this, listOf(appModule)) }
}

val appModule = module {
    single { RepositoryImpl() as Repository }
    viewModel { MainFragmentViewModel(get()) }
}