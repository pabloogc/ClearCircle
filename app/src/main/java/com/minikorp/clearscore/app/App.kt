package com.minikorp.clearscore.app

import android.app.Application
import com.minikorp.clearscore.BuildConfig
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import timber.log.Timber

private lateinit var _app: App
val app get() = _app

class App : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        _app = this

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Application wide [Kodein]. For Controllers / Repositories / Interactors.
     */
    override val kodein: Kodein by Kodein.lazy {
        import(AppModule.create())
    }
}