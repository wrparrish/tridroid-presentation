package com.tmsdurham.models

import com.squareup.moshi.Json


/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */

data class Address(val line1: String? = "", val line2: String? = "")

data class Attraction(val active: Boolean? = false,
                      val classifications: List<Classification>? = mutableListOf(),
                      val href: String? = "",
                      val id: String? = "",
                      val images: List<Image>? = mutableListOf(),
                      val _links: Links? = Links(),
                      val locale: String? = "",
                      val name: String? = "",
                      val references: References? = References(),
                      val source: Source? = Source(),
                      val test: Boolean?,
                      val type: String?,
                      val url: String?)

data class BoxOfficeInfo(val acceptedPaymentDetail: String? = "",
                         val openHoursDetail: String? = "",
                         val phoneNumberDetail: String? = "",
                         val willCallDetail: String? = "")

data class City(val name: String? = "")

data class Classification(val genre: Genre? = Genre(),
                          val primary: Boolean? = false,
                          val segment: Segment? = Segment(),
                          val subGenre: SubGenre? = SubGenre(),
                          val subType: Subtype? = Subtype(),
                          val type: Type? = Type())

data class Country(val countryCode: String? = "", val name: String? = "")

data class Dates(val start: Start? = Start(), val status: Status? = Status(), val timezone: String? = "")

data class DiscoverEventsResponse(val _embedded: Embedded? = Embedded(), val _links: Links? = Links(), val page: Page? = Page())

data class Dma(val id: String? = "")

data class Embedded(val attractions: List<Attraction>? = mutableListOf(), val events: List<Event>? = mutableListOf(), val venues: List<Venue>? = mutableListOf())

data class Event(val active: Boolean? = false,
                 val classifications: List<Classification>? = mutableListOf(),
                 val dates: Dates? = Dates(),
                 val _embedded: Embedded? = Embedded(),
                 val id: String? = "",
                 val images: List<Image>? = mutableListOf(),
                 val info: String? = "",
                 val _links: Links? = Links(),
                 val locale: String? = "",
                 val name: String? = "",
                 val pleaseNote: String? = "",
                 val priceRanges: List<PriceRange>? = mutableListOf(),
                 val promoter: Promoter? = Promoter(),
                 val references: References? = References(),
                 val sales: Sales? = Sales(),
                 val source: Source? = Source(),
                 val test: Boolean? = false,
                 val type: String? = "",
                 val url: String? = "")

data class GeneralInfo(val childRule: String? = "")

data class Genre(val id: String? = "", val name: String? = "")

data class Image(val fallback: Boolean? = false,
                 val height: Long? = 0,
                 val ratio: String? = "",
                 val url: String? = "",
                 val width: Long? = 0)

data class Links(val attractions: List<Attraction>? = mutableListOf(), val next: Next? = Next(), val self: Self? = Self(), val venues: List<Venue>? = mutableListOf())

data class Location(val latitude: String? = "", val longitude: String? = "")

data class Market(val name: String? = "", val id: Int? = 0)

data class Next(val href: String? = "", val templated: Boolean? = false)

data class Page(val number: Long? = 0, val size: Long? = 0, val totalElements: Long? = 0, val totalPages: Long? = 0)

data class PriceRange(val currency: String? = "", val max: Double? = 0.0, val min: Double? = 0.0, val type: String? = "")

data class Promoter(val description: String? = "", val id: String? = "", val name: String? = "")

data class Public(val endDateTime: String? = "", val startDateTime: String? = "", val startTBD: Boolean? = false)

data class References(
        val TMR: String? = "",
        @Json(name = "ticketmaster-tat") val tat: String? = "",
        @Json(name = "ticketmaster-uk") val uk: String? = "",
        @Json(name = "ticketmaster-us") val us: String? = "")

data class Sales(val public: Public? = Public())

data class Segment(val id: String? = "", val name: String? = "")

data class Self(val href: String? = "", val templated: Boolean? = false)

data class Source(val id: String? = "", val name: String? = "")

data class Start(val dateTBA: Boolean? = false,
                 val dateTBD: Boolean? = false,
                 val dateTime: String? = "",
                 val locale: String? = "",
                 val localTime: String? = "",
                 val noSpecificTime: Boolean? = false,
                 val timeTBA: Boolean? = false)

data class State(val name: String? = "", val stateCode: String? = "")

data class Status(val code: String? = "")

data class SubGenre(val id: String? = "", val name: String? = "")

data class Subtype(val id: String? = "", val name: String? = "")

data class Type(val id: String? = "", val name: String? = "")

data class Venue(val accessibleSeatingDetail: String? = "",
                 val active: Boolean? = false,
                 val address: Address? = Address(),
                 val boxOfficeInfo: BoxOfficeInfo? = BoxOfficeInfo(),
                 val city: City? = City(),
                 val country: Country? = Country(),
                 val dmas: List<Dma>? = mutableListOf(),
                 val generalInfo: GeneralInfo? = GeneralInfo(),
                 val href: String? = "",
                 val id: String? = "",
                 val _links: Links? = Links(),
                 val locale: String? = "",
                 val location: Location? = Location(),
                 val markets: List<Market>? = mutableListOf(),
                 val name: String? = "",
                 val parkingDetail: String? = "",
                 val postalCode: String? = "",
                 val references: References? = References(),
                 val source: Source? = Source(),
                 val state: State? = State(),
                 val test: Boolean? = false,
                 val timezone: String? = "",
                 val type: String? = "",
                 val url: String? = ""
)

data class InventoryResponse(
        val eventId: String? = null,
        val status: InventoryStatus? = null
)


enum class InventoryStatus {
    TICKETS_AVAILABLE,
    FEW_TICKETS_LEFT,
    TICKETS_NOT_AVAILABLE,
    UNKNOWN
}















