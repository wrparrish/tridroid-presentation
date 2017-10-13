package data.location

import domain_model.discover.Location
import rx.Observable

/**
 * Created by billyparrish on 5/8/17 for Shortlist.
 */
interface LocationSource {
    fun getLocation() : Observable<Location>
}