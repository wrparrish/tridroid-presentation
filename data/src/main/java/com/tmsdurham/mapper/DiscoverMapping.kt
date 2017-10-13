package com.tmsdurham.mapper

import com.tmsdurham.models.Attraction
import com.tmsdurham.models.DiscoverEventsResponse
import com.tmsdurham.models.Event
import com.tmsdurham.models.InventoryResponse
import com.tmsdurham.models.Sales
import domain_model.discover.Address
import domain_model.discover.BoxOfficeInfo
import domain_model.discover.City
import domain_model.discover.Classification
import domain_model.discover.Country
import domain_model.discover.Dates
import domain_model.discover.Dma
import domain_model.discover.Embedded
import domain_model.discover.GeneralInfo
import domain_model.discover.Genre
import domain_model.discover.Image
import domain_model.discover.InventoryStatus
import domain_model.discover.Links
import domain_model.discover.Location
import domain_model.discover.Market
import domain_model.discover.Next
import domain_model.discover.Page
import domain_model.discover.PriceRange
import domain_model.discover.Promoter
import domain_model.discover.Public
import domain_model.discover.References
import domain_model.discover.Segment
import domain_model.discover.Self
import domain_model.discover.Source
import domain_model.discover.Start
import domain_model.discover.State
import domain_model.discover.Status
import domain_model.discover.SubGenre
import domain_model.discover.Subtype
import domain_model.discover.Type
import domain_model.discover.Venue

/**
 * Created by billyparrish on 3/21/17 for Shortlist.
 */

fun DiscoverEventsResponse.toDomain(): domain_model.discover.DiscoverEventsResponse = domain_model.discover.DiscoverEventsResponse(
        this._embedded?.toDomain() ?: com.tmsdurham.models.Embedded().toDomain(),
        this._links?.toDomain() ?: com.tmsdurham.models.Links().toDomain(),
        this.page?.toDomain() ?: com.tmsdurham.models.Page().toDomain())

fun Attraction.toDomain(): domain_model.discover.Attraction {
    val dataClassifications = this.classifications
    val domainList = ArrayList<Classification>()
    dataClassifications?.forEach { domainList.add(it.toDomain()) }

    val dataImages = this.images
    val domainImageList = ArrayList<Image>()
    dataImages?.forEach { domainImageList.add(it.toDomain()) }


    return domain_model.discover.Attraction(this.active == true,
            domainList,
            this.href ?: "",
            this.id ?: "",
            domainImageList,
            this._links?.toDomain() ?: com.tmsdurham.models.Links().toDomain(),
            this.locale ?: "",
            this.name ?: "",
            this.references?.toDomain() ?: com.tmsdurham.models.References().toDomain(),
            this.source?.toDomain() ?: com.tmsdurham.models.Source().toDomain(),
            this.test == true,
            this.type ?: "",
            this.url ?: "")
}

fun Event.toDomain(): domain_model.discover.Event {
    val dataClassifications = this.classifications
    val domainClassifications = ArrayList<Classification>()
    dataClassifications?.forEach { domainClassifications.add(it.toDomain()) }

    val dataImages = this.images
    val domainImageList = ArrayList<Image>()
    dataImages?.forEach { domainImageList.add(it.toDomain()) }

    val dataPrices = this.priceRanges
    val domainPriceList = ArrayList<PriceRange>()
    dataPrices?.forEach { domainPriceList.add(it.toDomain()) }

    return domain_model.discover.Event(this.active == true,
            domainClassifications,
            this.dates?.toDomain() ?: com.tmsdurham.models.Dates().toDomain(),
            this._embedded?.toDomain() ?: com.tmsdurham.models.Embedded().toDomain(),
            this.id ?: "",
            domainImageList,
            this.info ?: "",
            this._links?.toDomain() ?: com.tmsdurham.models.Links().toDomain(),
            this.locale ?: "",
            this.name ?: "",
            this.pleaseNote ?: "",
            domainPriceList,
            this.promoter?.toDomain() ?: com.tmsdurham.models.Promoter().toDomain(),
            this.references?.toDomain() ?: com.tmsdurham.models.References().toDomain(),
            this.sales?.toDomain() ?: Sales().toDomain(),
            this.source?.toDomain() ?: com.tmsdurham.models.Source().toDomain(),
            this.test == true,
            this.type ?: "",
            this.url ?: ""
    )
}

fun Sales.toDomain(): domain_model.discover.Sales = domain_model.discover.Sales(this.public?.toDomain() ?: com.tmsdurham.models.Public().toDomain())

fun com.tmsdurham.models.Embedded.toDomain(): Embedded {
    val dataAttractions = this.attractions
    val domainAttractions = ArrayList<domain_model.discover.Attraction>()
    dataAttractions?.forEach { domainAttractions.add(it.toDomain()) }

    val dataVenues = this.venues
    val domainVenues = ArrayList<Venue>()
    dataVenues?.forEach { domainVenues.add(it.toDomain()) }

    val dataEvents = this.events
    val domainEvents = ArrayList<domain_model.discover.Event>()
    dataEvents?.forEach { domainEvents.add(it.toDomain()) }

    return Embedded(domainAttractions, domainEvents, domainVenues
    )
}

fun com.tmsdurham.models.Links.toDomain(): Links {
    val dataAttractions = this.attractions
    val domainAttractions = ArrayList<domain_model.discover.Attraction>()
    dataAttractions?.forEach { domainAttractions.add(it.toDomain()) }

    val dataVenues = this.venues
    val domainVenues = ArrayList<Venue>()
    dataVenues?.forEach { domainVenues.add(it.toDomain()) }


    return Links(domainAttractions,
            this.next?.toDomain() ?: com.tmsdurham.models.Next().toDomain(),
            this.self?.toDomain() ?: com.tmsdurham.models.Self().toDomain(),
            domainVenues)

}


fun com.tmsdurham.models.Venue.toDomain(): Venue {
    val dataDmas = this.dmas
    val domainDmaList = ArrayList<Dma>()
    dataDmas?.forEach { domainDmaList.add(it.toDomain()) }


    val dataMarketList = this.markets
    val domainMarketList = ArrayList<Market>()
    dataMarketList?.forEach { domainMarketList.add(it.toDomain()) }

    return Venue(this.accessibleSeatingDetail ?: "",
            this.active == true,
            this.address?.toDomain() ?: com.tmsdurham.models.Address().toDomain(),
            this.boxOfficeInfo?.toDomain() ?: com.tmsdurham.models.BoxOfficeInfo().toDomain(),
            this.city?.toDomain() ?: com.tmsdurham.models.City().toDomain(),
            this.country?.toDomain() ?: com.tmsdurham.models.Country().toDomain(),
            domainDmaList,
            this.generalInfo?.toDomain() ?: com.tmsdurham.models.GeneralInfo().toDomain(),
            this.href ?: "",
            this.id ?: "",
            this._links?.toDomain() ?: com.tmsdurham.models.Links().toDomain(),
            this.locale ?: "",
            this.location?.toDomain() ?: com.tmsdurham.models.Location().toDomain(),
            domainMarketList,
            this.name ?: "",
            this.parkingDetail ?: "",
            this.postalCode ?: "",
            this.references?.toDomain() ?: com.tmsdurham.models.References().toDomain(),
            this.source?.toDomain() ?: com.tmsdurham.models.Source().toDomain(),
            this.state?.toDomain() ?: com.tmsdurham.models.State().toDomain(),
            this.test == true,
            this.timezone ?: "",
            this.type ?: "",
            this.url ?: "")
}


fun com.tmsdurham.models.Address.toDomain(): Address = Address(this.line1 ?: "",
        this.line2 ?: "")


fun com.tmsdurham.models.Country.toDomain(): Country = Country(this.countryCode ?: "",
        this.name ?: "")

fun com.tmsdurham.models.Classification.toDomain(): Classification = Classification(this.genre?.toDomain() ?: com.tmsdurham.models.Genre().toDomain(),
        this.primary == true,
        this.segment?.toDomain() ?: com.tmsdurham.models.Segment().toDomain(),
        this.subGenre?.toDomain() ?: com.tmsdurham.models.SubGenre().toDomain(),
        this.subType?.toDomain() ?: com.tmsdurham.models.Subtype().toDomain(),
        this.type?.toDomain() ?: com.tmsdurham.models.Type().toDomain())

fun com.tmsdurham.models.City.toDomain(): City = City(this.name ?: "")

fun com.tmsdurham.models.BoxOfficeInfo.toDomain(): BoxOfficeInfo = BoxOfficeInfo(this.acceptedPaymentDetail ?: "",
        this.openHoursDetail ?: "",
        this.phoneNumberDetail ?: "",
        this.willCallDetail ?: "")

fun com.tmsdurham.models.Promoter.toDomain(): Promoter = Promoter(this.description ?: "",
        this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.PriceRange.toDomain(): PriceRange = PriceRange(this.currency ?: "",
        this.max ?: 0.0,
        this.min ?: 0.0,
        this.type ?: "")

fun com.tmsdurham.models.Page.toDomain(): Page = Page(this.number ?: 0,
        this.size ?: 0,
        this.totalElements ?: 0,
        this.totalPages ?: 0)

fun com.tmsdurham.models.Next.toDomain(): Next = Next(this.href ?: "",
        this.templated == true)

fun com.tmsdurham.models.Location.toDomain(): Location = Location(this.latitude ?: "",
        this.longitude ?: "")

fun com.tmsdurham.models.Market.toDomain(): Market = Market(this.name ?: "", this.id ?: 0)

fun com.tmsdurham.models.Genre.toDomain(): Genre = Genre(this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.GeneralInfo.toDomain(): GeneralInfo = GeneralInfo(this.childRule ?: "")

fun com.tmsdurham.models.Image.toDomain(): Image = Image(this.fallback == true,
        this.height ?: 0,
        this.ratio ?: "",
        this.url ?: "",
        this.width ?: 0)

fun com.tmsdurham.models.Dma.toDomain(): Dma = Dma(this.id ?: "")

fun com.tmsdurham.models.Dates.toDomain(): Dates = Dates(this.start?.toDomain() ?: com.tmsdurham.models.Start().toDomain(),
        this.status?.toDomain() ?: com.tmsdurham.models.Status().toDomain(),
        this.timezone ?: "")


fun com.tmsdurham.models.Type.toDomain(): Type = Type(this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.Subtype.toDomain(): Subtype = Subtype(this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.SubGenre.toDomain(): SubGenre = SubGenre(this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.Status.toDomain(): Status = Status(this.code ?: "")

fun com.tmsdurham.models.State.toDomain(): State = State(this.name ?: "",
        this.stateCode ?: "")

fun com.tmsdurham.models.Start.toDomain(): Start = Start(this.dateTBA == true,
        this.dateTBD == true,
        this.dateTime ?: "",
        this.locale ?: "",
        this.localTime ?: "",
        this.noSpecificTime == true,
        this.timeTBA == true)

fun com.tmsdurham.models.Source.toDomain(): Source = Source(this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.Self.toDomain(): Self = Self(this.href ?: "",
        this.templated == true)

fun com.tmsdurham.models.Segment.toDomain(): Segment = Segment(this.id ?: "",
        this.name ?: "")

fun com.tmsdurham.models.References.toDomain(): References = References(this.TMR ?: "",
        this.tat ?: "",
        this.uk ?: "",
        this.us ?: "")

fun com.tmsdurham.models.Public.toDomain(): Public = Public(this.endDateTime ?: "",
        this.startDateTime ?: "",
        this.startTBD == true)

fun InventoryResponse.toDomain(): domain_model.discover.InventoryResponse = domain_model.discover.InventoryResponse(this.eventId ?: "", InventoryStatus.valueOf(this.status?.name ?: "UNKNOWN"))
