package com.tmsdurham.tridroidredux.common.inject.module

import android.content.Context

import com.squareup.moshi.Moshi
import com.tmsdurham.data.preference.SimplePreference
import com.tmsdurham.data.retrofit.EventRetrofitDataSource
import com.tmsdurham.data.retrofit.service.EventService
import com.tmsdurham.data.retrofit.service.TopPickService


import com.tmsdurham.tridroidredux.BuildConfig
import dagger.Module
import dagger.Provides
import data.KVPreference

import data.event.EventNetworkDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class DataModule {
    private val TIMEOUT_MS = 30000L
    private val isLoggingEnabled = BuildConfig.DEBUG
    private val DISCOVERY_PROD_BASE = "https://app.ticketmaster.com/"


    @Provides
    @Singleton
    fun moshi(): Moshi {
        return Moshi.Builder().build()
    }


    @Provides
    @Singleton
    fun providesTmClient(): OkHttpClient {
        val discoveryClient = OkHttpClient.Builder()
                .readTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(getCommonLogging())
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val originalUrl = originalRequest.url()
                    val newUrl = originalUrl.newBuilder()
                            .addQueryParameter("apikey", BuildConfig.DISCOVER_API_KEY)
                            .build()

                    chain.proceed(originalRequest
                            .newBuilder()
                            .url(newUrl)
                            .build())
                }
                .build()

        return discoveryClient
    }

    @Provides
    @Singleton
    fun providesTmRetrofit(tmClient: OkHttpClient, moshi: Moshi): Retrofit {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(DISCOVERY_PROD_BASE)
                .client(tmClient)
                .build()

        return retrofit
    }




    @Provides
    @Singleton
    fun providesEventService(tmRetrofit: Retrofit): EventService {
        return tmRetrofit.create<EventService>(EventService::class.java)
    }





    @Provides
    @Singleton
    fun providesTopPicksApi(moshi: Moshi, tmclient: OkHttpClient): TopPickService {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(DISCOVERY_PROD_BASE)
                .client(tmclient)
                .build()

        return retrofit.create<TopPickService>(TopPickService::class.java)
    }




    @Provides
    @Singleton
    fun eventNetworkDataSource(eventService: EventService): EventNetworkDataSource {
        return EventRetrofitDataSource(eventService)
    }

    @Provides
    @Singleton
    fun providesPreference(context: Context): KVPreference {
        val sharedPref = context.getSharedPreferences("default", Context.MODE_PRIVATE);
        return SimplePreference(sharedPref)
    }



    fun getCommonLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        val logLevel = if (isLoggingEnabled)
            HttpLoggingInterceptor.Level.BASIC
        else
            HttpLoggingInterceptor.Level.NONE
        logging.level = logLevel
        return logging
    }
}