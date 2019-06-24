package com.minikorp.clearscore.core.data

import com.minikorp.clearscore.app.error.DefaultErrorHandler
import com.minikorp.clearscore.app.error.ErrorHandler
import com.minikorp.clearscore.core.data.network.ClearScoreApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Application wide module, holding bindings to communicate with the API (Retrofit, Moshi, API itself..)
 */
object ScoreModule {
    fun create(): Kodein.Module = Kodein.Module("ScoreModule") {

        bind<Moshi>() with singleton {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                //TODO not hardcoded, move it build constant probably
                .baseUrl("https://5lfoiyb0b3.execute-api.us-west-2.amazonaws.com/prod/")
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build()
        }

        bind<ErrorHandler>() with singleton { DefaultErrorHandler() }

        bind<ClearScoreApi>() with singleton {
            instance<Retrofit>().create(ClearScoreApi::class.java)
        }

        bind<ScoreRepository>() with singleton {
            NetworkScoreRepository(instance())
        }
    }
}