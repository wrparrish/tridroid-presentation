package com.tmsdurham.data.retrofit

import com.tmsdurham.data.retrofit.service.TopPickService
import data.pick.PickNetworkDataSource
import domain_model.discover.Event
import domain_model.top_picks.TopPickResponse
import getLegacyId
import rx.Observable
import use_case.pick.GetPicks
import javax.inject.Inject

/**
 * Created by billyparrish on 3/13/17 for Shortlist.
 */
class TopPickRetrofitDataSource @Inject constructor(private val pickService: TopPickService) : PickNetworkDataSource {
    override fun getAll(): Observable<List<TopPickResponse>> = Observable.empty()

    override fun get(key: String): Observable<TopPickResponse> =
            pickService.getTopPicks(key, HashMap())


    override fun put(key: String, value: TopPickResponse): Observable<TopPickResponse> =
            Observable.empty()

    override fun remove(key: String): Observable<TopPickResponse> = Observable.empty()

    override fun getPickforEvent(event: Event, quantity: Int): Observable<GetPicks.Response> {
        val paramMap = HashMap<String, String>()
        paramMap.put("quantity", quantity.toString())
        return pickService.getTopPicks(event.getLegacyId(), paramMap)
                .onErrorResumeNext {
                    Observable.empty() }
                .filter { it != null && it.picks.isNotEmpty() }
                .map { GetPicks.Response(event to it) }

    }
}