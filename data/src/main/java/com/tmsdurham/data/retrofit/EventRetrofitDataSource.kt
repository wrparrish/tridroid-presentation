package com.tmsdurham.data.retrofit

import com.tmsdurham.data.retrofit.service.EventService
import com.tmsdurham.mapper.toDomain
import data.event.EventNetworkDataSource
import domain_model.discover.DiscoverEventsResponse
import domain_model.discover.Event
import domain_model.discover.InventoryResponse
import rx.Observable
import javax.inject.Inject

/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */

class EventRetrofitDataSource @Inject constructor(private val eventService: EventService) : EventNetworkDataSource {


    override fun getInventoryStatus(csvIdString: String): Observable<List<InventoryResponse>> =
            eventService.getInventoryStatus(csvIdString, inventoryKey)
            .flatMap { Observable.from(it) }
            .map { it.toDomain() }
            .toList()


    override fun getEvents(map: Map<String, String>): Observable<DiscoverEventsResponse> = eventService.getEvents(map)
            .map { it.toDomain() }


    override fun getAll(): Observable<List<Event>> = throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.

    override fun get(key: String): Observable<Event> = throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.

    override fun put(key: String, value: Event): Observable<Event> = throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.

    override fun remove(key: String): Observable<Event> = throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.

    companion object {
        val inventoryKey = "d0fiuXRg2dzwu6qTuef7Jt01rtoGXLWk"
    }

}
