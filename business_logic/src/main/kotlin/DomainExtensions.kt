
import domain_model.discover.Attraction
import domain_model.discover.Event
import domain_model.discover.Image
import domain_model.discover.Venue
import domain_model.top_picks.Offer
import domain_model.top_picks.Pick
import domain_model.top_picks.TopPickResponse
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */

/**
 * returns tap event id for us region
 */
fun Event.getLegacyId(): String = this.references.us

fun Event.getStartMillis(): Long {
    val timeString = this.dates.start.dateTime
    val timezone = TimeZone.getTimeZone(this.dates.timezone)
    val zone = DateTimeZone.forTimeZone(timezone)
    val eventTime = DateTime(timeString, zone)
    return eventTime.millis
}



fun Event.timeCheck(): Boolean {
    val timeString = this.dates.start.dateTime
    if (timeString.isEmpty()) return false
    val timezone = TimeZone.getTimeZone(this.dates.timezone)
    val zone = DateTimeZone.forTimeZone(timezone)
    val eventTime = DateTime(timeString, zone)
    val timeNow = DateTime(zone)


    return timeNow.millis < eventTime.millis
}

fun Event.undefinedGenreCheck(): Boolean =
        this.classifications.any { it.genre.name == "Undefined" || it.genre.name.isBlank() || it.genre.name == "Comedy" }

/**
 * return tap attraction id for us region
 */
fun Attraction.getLegacyId(): String = this.references.us

/**
 *  checks a top pick response for a list of picks, and returns the first ( highest ranked ) one
 */
fun TopPickResponse.getTopPick(): Pick? {
    val pickList = this.picks
    if (pickList.isNotEmpty()) {
        return pickList.first()
    }
    return null
}

/**
 * returns the embedded attraction for a given event
 */
fun Event.getAttraction(): Attraction? {
    val embedded = this._embedded
    val attractions = embedded.attractions
    if (attractions != null && attractions.isNotEmpty()) {
        val attraction = attractions.first()
        if (attraction.id != null && attraction.id.isNotEmpty()) {
            return attraction
        }
    }
    return null
}

fun Event.getAttractionList(): List<Attraction> {
    val embedded = this._embedded
    val attractions = embedded.attractions
    return if (attractions != null && attractions.isNotEmpty()) {
        attractions
    } else {
        mutableListOf()
    }
}

/**
 * returns the embedded for venue for a given event
 */
fun Event.getVenue(): Venue? {
    val embedded = this._embedded
    val venues = embedded.venues
    if (venues != null && venues.isNotEmpty()) {
        val venue = venues.first()
        if (venue.id != null && venue.id.isNotEmpty()) {
            return venue
        }
    }
    return null
}

/**
 * Gets the tallest image available
 */
fun Event.getTallestImage(): Image {
    val sorted = this.images.sortedByDescending { it.height }
    return sorted.first()
}

/**
 * Gets the  image with smallest difference between the provided view available
 */
fun Event.getClosestImage(viewHeight: Int, viewWidth: Int): Image {
    val nonFallbacks = this.images.filter { !it.fallback }
    return if (nonFallbacks.isNotEmpty()) {
        nonFallbacks.sortedBy { Math.abs((it.height - viewHeight) + it.width - viewWidth) }.first()
    } else {
        val sorted = this.images.sortedBy { Math.abs((it.height - viewHeight) + it.width - viewWidth) }
        sorted.first()
    }

}


/**
 * returns the city and state of the venue
 */
fun Venue.locationString(): String = "${this.city.name}, ${this.state.name}"


fun TopPickResponse.hasPicks(): Boolean = this.picks.isNotEmpty()

fun TopPickResponse.cheapestPickAndOffer(): Pair<Pick?, Offer?>  {
    val offersList: List<Offer> = this._embedded.offer

    val cheapestPick = this.picks.minBy {
        val pickOfferId = it.offers.first()
        val offerForPick = offersList.firstOrNull { it.offerId == pickOfferId }
        val sortValue = offerForPick?.totalPrice ?: Double.MAX_VALUE
        sortValue
    }

    val offerForCheapestPick = offersList.firstOrNull { it.offerId == cheapestPick?.offers?.first() }
    return cheapestPick to offerForCheapestPick
}

fun TopPickResponse.bestPickAndOffer(): Pair<Pick?, Offer?> {
    val offersList: List<Offer> = this._embedded.offer
    val bestPick = this.picks.first()
    val bestOffer = offersList.firstOrNull { it.offerId == bestPick.offers.first() }
    return bestPick to bestOffer
}






