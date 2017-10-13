package use_case.event

import data.event.EventNetworkDataSource
import domain_model.discover.InventoryResponse
import rx.Observable
import rx.schedulers.Schedulers
import use_case.UseCase
import javax.inject.Inject

/**
 * Created by billyparrish on 8/18/17 for Shortlist-Android.
 */
class GetInventoryStatus @Inject constructor(val network: EventNetworkDataSource) : UseCase<GetInventoryStatus.Request, GetInventoryStatus.Response> {

    override fun execute(request: Request): Observable<Response> = network.getInventoryStatus(request.csvString)
            .map { Response(it) }
            .subscribeOn(Schedulers.io())

    class Request(val csvString: String) : UseCase.Request
    class Response(val statusArray: List<InventoryResponse>) : UseCase.Response
}