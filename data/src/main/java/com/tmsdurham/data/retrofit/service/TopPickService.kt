package com.tmsdurham.data.retrofit.service

import domain_model.top_picks.TopPickResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by billyparrish on 2/28/17 for Shortlist.
 */
interface TopPickService {

    @GET("top-picks/v1/events/{eventId}")
    fun getTopPicks(@Path("eventId") eventId: String, @QueryMap options: Map<String, String>): Observable<TopPickResponse>
}
