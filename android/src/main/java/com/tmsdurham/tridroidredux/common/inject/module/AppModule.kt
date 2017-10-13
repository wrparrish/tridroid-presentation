package com.tmsdurham.tridroidredux.common.inject.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

/**
 * Created by billyparrish on 3/3/17 for Shortlist.
 */


@Module
class AppModule(val application: Application) {

    @Provides
    fun providesApplicationContext(): Context {
        return application.baseContext
    }


    @Provides
    fun providesResources(context: Context): Resources {
        return context.resources
    }


}