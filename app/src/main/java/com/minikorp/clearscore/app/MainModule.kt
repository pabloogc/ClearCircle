package com.minikorp.clearscore.app

import com.minikorp.clearscore.app.home.HomeModule
import com.minikorp.clearscore.util.KodeinViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Module for activity
 */
object MainModule {
    fun create() = Kodein.Module("MainModule") {
        //Bind view model factory so ViewModels can be created with dependencies
        bind<KodeinViewModelFactory>() with singleton { KodeinViewModelFactory(kodein.direct) }
        import(HomeModule.create())
    }
}