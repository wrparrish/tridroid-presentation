package com.tmsdurham.tridroidredux.common.inject.module

import dagger.Module
import dagger.Provides
import data.event.EventDiskDataSource
import data.event.EventMemoryDataSource
import data.event.EventNetworkDataSource
import data.pick.PickNetworkDataSource
import use_case.event.GetEvents
import use_case.event.GetInventoryStatus
import use_case.event.SaveEvents
import use_case.pick.GetPicks
import javax.inject.Singleton



/**
 * Created by billyparrish on 3/17/17 for Shortlist.
 */
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPicksUseCase(network: PickNetworkDataSource): GetPicks {
        return GetPicks(network)
    }


    @Provides
    @Singleton
    fun provideSaveEventsUseCase(disk: EventDiskDataSource, memory: EventMemoryDataSource): SaveEvents {
        return SaveEvents(disk, memory)
    }

    @Provides
    @Singleton
    fun provideGetEventsUseCase(network: EventNetworkDataSource): GetEvents {
        return GetEvents(network)
    }

    @Provides
    @Singleton
    fun providesGetInventoryUseCase(network: EventNetworkDataSource): GetInventoryStatus {
        return GetInventoryStatus(network)
    }

}