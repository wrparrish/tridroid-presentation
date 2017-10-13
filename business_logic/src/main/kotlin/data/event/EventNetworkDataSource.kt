package data.event

import domain_model.discover.DiscoverEventsResponse
import domain_model.discover.InventoryResponse
import rx.Observable


interface EventNetworkDataSource : EventDataSource {
    fun getEvents(map: Map<String, String>): Observable<DiscoverEventsResponse>
    fun getInventoryStatus(csvIdString: String): Observable<List<InventoryResponse>>
}