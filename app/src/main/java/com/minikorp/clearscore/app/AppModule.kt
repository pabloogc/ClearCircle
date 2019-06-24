package com.minikorp.clearscore.app

import com.minikorp.clearscore.core.data.ScoreModule
import org.kodein.di.Kodein

object AppModule {
    fun create() = Kodein.Module("AppModule") {
        import(ScoreModule.create())
    }
}