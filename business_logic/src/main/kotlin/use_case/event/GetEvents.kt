package use_case.event

import data.event.EventNetworkDataSource
import domain_model.discover.Event
import rx.Observable
import rx.schedulers.Schedulers
import use_case.Strategy
import use_case.UseCase
import javax.inject.Inject

/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */

class GetEvents @Inject constructor(
        private val network: EventNetworkDataSource) : UseCase<GetEvents.Request, GetEvents.Response> {


    override fun execute(request: Request): Observable<GetEvents.Response> = network.getEvents(request.params)
            .map { it._embedded?.events ?: ArrayList() }
            .map { Response(it) }
            .subscribeOn(Schedulers.io())

    class Request(val params: Map<String, String>) : UseCase.Request

    class Response(val events: List<Event>) : UseCase.Response
}









