package com.minikorp.clearscore.app.home

import com.minikorp.clearscore.util.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 * Module for home. Quite basic for now (only holding ViewModel) but would grow.
 */
object HomeModule {
    fun create() = Kodein.Module("HomeModule") {
        bindViewModel<ScoreViewModel>() with provider {
            ScoreViewModel(instance())
        }
    }
}