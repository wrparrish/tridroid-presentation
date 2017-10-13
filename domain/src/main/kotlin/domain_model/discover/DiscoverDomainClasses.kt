/**
 * Created by billyparrish on 10/12/17.
 */


package domain_model.discover

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */

data class Address(val line1: String, val line2: String) : Serializable

data class Attraction(val active: Boolean,
                      val classifications: List<Classification>,
                      val href: String,
                      val id: String,
                      val images: List<Image>,
                      val _links: Links,
                      val locale: String,
                      val name: String,
                      val references: References,
                      val source: Source,
                      val test: Boolean,
                      val type: String,
                      val url: String) : Serializable

data class BoxOfficeInfo(val acceptedPaymentDetail: String,
                         val openHoursDetail: String,
                         val phoneNumberDetail: String,
                         val willCallDetail: String) : Serializable

data class City(val name: String) : Serializable

data class Classification(val genre: Genre,
                          val primary: Boolean,
                          val segment: Segment,
                          val subGenre: SubGenre,
                          val subType: Subtype,
                          val type: Type) : Serializable

data class Country(val countryCode: String, val name: String) : Serializable

data class Dates(val start: Start, val status: Status, val timezone: String) : Serializable

data class DiscoverEventsResponse(val _embedded: Embedded?, val _links: Links, val page: Page) : Serializable

data class Dma(val id: String) : Serializable

data class Embedded(val attractions: List<Attraction>, val events: List<Event>, val venues: List<Venue>) : Serializable

data class Event(val active: Boolean,
                 val classifications: List<Classification>,
                 val dates: Dates,
                 val _embedded: Embedded,
                 val id: String,
                 val images: List<Image>,
                 val info: String,
                 val _links: Links,
                 val locale: String?,
                 val name: String,
                 val pleaseNote: String,
                 val priceRanges: List<PriceRange>,
                 val promoter: Promoter,
                 val references: References,
                 val sales: Sales,
                 val source: Source,
                 val test: Boolean,
                 val type: String,
                 val url: String) : Serializable

data class GeneralInfo(val childRule: String) : Serializable

data class Genre(val id: String, val name: String) : Serializable

data class Image(val fallback: Boolean,
                 val height: Long,
                 val ratio: String,
                 val url: String,
                 val width: Long) : Serializable

data class Links(val attractions: List<Attraction>, val next: Next, val self: Self, val venues: List<Venue>) : Serializable

data class Location(val latitude: String, val longitude: String) : Serializable

data class Market(val name: String, val id: Int) : Serializable

data class Next(val href: String, val templated: Boolean) : Serializable

data class Page(val number: Long, val size: Long, val totalElements: Long, val totalPages: Long) : Serializable

data class PriceRange(val currency: String, val max: Double, val min: Double, val type: String) : Serializable

data class Promoter(val description: String, val id: String, val name: String) : Serializable

data class Public(val endDateTime: String, val startDateTime: String, val startTBD: Boolean) : Serializable

data class References(
        val TMR: String,
        @Json(name = "ticketmaster-tat") val tat: String,
        @Json(name = "ticketmaster-uk") val uk: String,
        @Json(name = "ticketmaster-us") val us: String) : Serializable

data class Sales(val public: Public) : Serializable

data class Segment(val id: String, val name: String) : Serializable

data class Self(val href: String, val templated: Boolean) : Serializable

data class Source(val id: String, val name: String) : Serializable

data class Start(val dateTBA: Boolean,
                 val dateTBD: Boolean,
                 val dateTime: String,
                 val locale: String?,
                 val localTime: String,
                 val noSpecificTime: Boolean,
                 val timeTBA: Boolean) : Serializable

data class State(val name: String, val stateCode: String) : Serializable

data class Status(val code: String) : Serializable

data class SubGenre(val id: String, val name: String) : Serializable

data class Subtype(val id: String, val name: String) : Serializable

data class Type(val id: String, val name: String) : Serializable

data class Venue(val accessibleSeatingDetail: String,
                 val active: Boolean,
                 val address: Address,
                 val boxOfficeInfo: BoxOfficeInfo,
                 val city: City,
                 val country: Country,
                 val dmas: List<Dma>,
                 val generalInfo: GeneralInfo,
                 val href: String,
                 val id: String,
                 val _links: Links,
                 val locale: String,
                 val location: Location,
                 val markets: List<Market>,
                 val name: String,
                 val parkingDetail: String,
                 val postalCode: String,
                 val references: References,
                 val source: Source,
                 val state: State,
                 val test: Boolean,
                 val timezone: String,
                 val type: String,
                 val url: String) : Serializable

data class InventoryResponse(
        val eventId: String,
        val status: InventoryStatus
)

enum class InventoryStatus {
    TICKETS_AVAILABLE,
    FEW_TICKETS_LEFT,
    TICKETS_NOT_AVAILABLE,
    UNKNOWN
}













