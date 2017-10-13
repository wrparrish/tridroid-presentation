package com.tmsdurham.tridroidredux

import android.app.Application
import com.tmsdurham.tridroidredux.common.inject.module.AppModule
import com.tmsdurham.tridroidredux.common.inject.module.DataModule
import com.tmsdurham.tridroidredux.common.inject.module.PresentationModule
import com.tmsdurham.tridroidredux.common.inject.Injector
import com.tmsdurham.tridroidredux.common.inject.component.DaggerShortListComponent
import com.tmsdurham.tridroidredux.common.inject.component.ShortListComponent

/**
 * Created by billyparrish on 10/12/17.
 */
class TridroidApp: Application() {
    private var injector: Injector? = null

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        val component = createComponent()
        injector = Injector.get(component)
    }


    protected open fun createComponent(): ShortListComponent {
        return DaggerShortListComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .presentationModule(PresentationModule())
                .build()
    }
}