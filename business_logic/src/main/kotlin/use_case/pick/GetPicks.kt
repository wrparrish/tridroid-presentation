package use_case.pick

import data.pick.PickNetworkDataSource
import domain_model.discover.Event
import domain_model.top_picks.TopPickResponse
import rx.Observable
import rx.schedulers.Schedulers
import use_case.UseCase
import javax.inject.Inject

/**
 * Created by billyparrish on 3/13/17 for Shortlist.
 */
class GetPicks @Inject constructor(val network: PickNetworkDataSource) : UseCase<GetPicks.Request, GetPicks.Response> {
    override fun execute(request: Request): Observable<Response> = Observable.from(request.events)
            .concatMapEager{
                network.getPickforEvent(it)
                        .filter { it.pair != null &&  it.pair.second._embedded.offer.isNotEmpty() }
                        .subscribeOn(Schedulers.io())
            }

    class Request(val events: List<Event>) : UseCase.Request
    class Response(val pair: Pair<Event, TopPickResponse>?) : UseCase.Response
}