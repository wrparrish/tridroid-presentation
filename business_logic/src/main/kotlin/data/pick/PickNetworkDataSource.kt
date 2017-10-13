package data.pick

import data.DataSource
import domain_model.discover.Event
import domain_model.top_picks.TopPickResponse
import rx.Observable
import use_case.pick.GetPicks

/**
 * Created by billyparrish on 3/13/17 for Shortlist.
 */
interface PickNetworkDataSource : DataSource<String, TopPickResponse> {

    fun getPickforEvent(event: Event, quantity: Int = 2): Observable<GetPicks.Response>

}