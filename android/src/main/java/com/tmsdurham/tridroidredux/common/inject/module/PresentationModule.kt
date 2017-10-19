package com.tmsdurham.tridroidredux.common.inject.module


import com.tmsdurham.tridroidredux.event_discover.cityMap
import com.tmsdurham.tridroidredux.event_discover.presenter.DiscoverPresenter
import dagger.Module
import dagger.Provides
import data.location.ShortListCity
import com.tmsdurham.DiscoverModel
import use_case.event.GetEvents
import use_case.event.GetInventoryStatus
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by billyparrish on 3/10/17 for Shortlist.
 */
@Module
class PresentationModule {
    @Singleton
    @Provides
    fun providesSearchPresenter(model: DiscoverModel,
                                events: GetEvents,
                                inventoryStatus: GetInventoryStatus): DiscoverPresenter {

        return DiscoverPresenter(model, events, inventoryStatus)
    }




    @Provides
    @Singleton
    fun providesDiscoverViewModel(): DiscoverModel {
        return DiscoverModel(cityMap)
    }




    @Named("cityMap")
    @Singleton
    @Provides
    fun providesCityMap(): MutableMap<ShortListCity, Boolean> {
        return cityMap
    }

}