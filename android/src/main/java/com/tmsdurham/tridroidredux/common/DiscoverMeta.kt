package com.tmsdurham.tridroidredux.common

import android.content.res.Resources
import android.util.TypedValue
import domain_model.discover.Event
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit



/**
 * Created by billyparrish on 3/13/17 for Shortlist.
 */

val NUM_DAYS: Long = 7
val SEARCH_SIZE = "500"

fun dpToPx(dp: Float, context: android.content.Context):Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()


fun getEndDateTime(NUM_DAYS: Long): String {
    val daysInMillis = TimeUnit.DAYS.toMillis(NUM_DAYS)
    val currentTimeInMillis = System.currentTimeMillis()
    val currentPlusDaysInMillis = currentTimeInMillis + daysInMillis
    val endDate = DateTime(currentPlusDaysInMillis)

    val pattern = "yyyy-MM-dd'T'HH:mm:ss"
    val dtf = DateTimeFormat.forPattern(pattern)
    return dtf.print(endDate) + "Z"
}




fun getScreenWidth(): Int {
    return Resources.getSystem().getDisplayMetrics().widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().getDisplayMetrics().heightPixels
}



object KotlinBridge {
    val artistInfoMap: ConcurrentHashMap<String, HashMap<String, String>> = ConcurrentHashMap()
    val keyTwitter = "twitter_key"
    val keyInstagram = "instagram_key"
    val keySpotify = "spotify_key"
    val keyFacebook = "facebook_key"
    val keyHomePage = "homepage_key"
    val keyBazaarRating = "bazaar_rating_key"
    val keyBazaarCount = "bazaar_count_key"
    val keyWebUrl = "web_url_key"
    val keyWebDescription = "web_description"


    fun getArtistInfoItem(id: String, key: String): String {
        val item = artistInfoMap[id]?.get(key)
        return item.orEmpty()
    }


    fun time(event: Event?): String {
        val timeString = event?.dates?.start?.dateTime
        val javaTimeZone = TimeZone.getTimeZone(event?.dates?.timezone ?: "")
        val zone = DateTimeZone.forTimeZone(javaTimeZone)
        val eventTime = DateTime(timeString, zone)
        val timeNow = DateTime()

        val eventDayOfWeek = eventTime.dayOfWeek
        val eventDayOfMonth = eventTime.dayOfMonth
        val nowDayOfMonth = timeNow.dayOfMonth

        val day: String = if (eventDayOfMonth == nowDayOfMonth) "Today"
        else if (eventDayOfMonth - nowDayOfMonth == 1) {
            "Tomorrow"
        } else {
            when (eventDayOfWeek) {
                DateTimeConstants.MONDAY -> "Monday,"

                DateTimeConstants.TUESDAY -> "Tuesday,"

                DateTimeConstants.WEDNESDAY -> "Wednesday,"

                DateTimeConstants.THURSDAY -> "Thursday,"

                DateTimeConstants.FRIDAY -> "Friday,"

                DateTimeConstants.SATURDAY -> "Saturday,"

                DateTimeConstants.SUNDAY -> "Sunday,"

                else -> ""
            }

        }

        val formatter = DateTimeFormat.forPattern("h:mm a")
        val hour = formatter.print(eventTime)
        return day + " " + hour

    }

    //todo --  any needed extension functions can be reached from java by building a method thats calls into the kotlin extension
}