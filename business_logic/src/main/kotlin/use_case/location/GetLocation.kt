package use_case.location

import data.location.LocationSource
import domain_model.discover.Location
import rx.Observable
import rx.schedulers.Schedulers
import use_case.UseCase
import javax.inject.Inject

/**
 * Created by billyparrish on 5/8/17 for Shortlist.
 */
class GetLocation @Inject constructor(private val locationProvider : LocationSource): UseCase<GetLocation.Request, GetLocation.Response>{
    override fun execute(request: Request): Observable<Response> = locationProvider.getLocation()
            .map { Response(Location(it.latitude, it.longitude)) }
            .subscribeOn(Schedulers.io())


    class Request : UseCase.Request
    class Response(val location: Location) : UseCase.Response
}