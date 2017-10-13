package com.tmsdurham.data.retrofit.service


import com.tmsdurham.models.DiscoverEventsResponse
import com.tmsdurham.models.InventoryResponse
import retrofit2.http.*
import rx.Observable


/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 * This classes makes use of DataModel types,  do not import or use the domain models in this interface.
 */
interface EventService {
    @GET("discovery/v2/events/")
    fun getEvents(@QueryMap options: Map<String, String>): Observable<DiscoverEventsResponse>

    @POST("/inventory-status/v1/availability")
    fun getInventoryStatus(@Query("events") csvString: String, @Query("apikey") key: String): Observable<List<InventoryResponse>>  // horseshit key shenanigans


}