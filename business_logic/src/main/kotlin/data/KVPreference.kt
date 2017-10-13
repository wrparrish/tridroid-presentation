package data

/**
 * Created by billyparrish on 5/22/17 for Shortlist.
 */
interface KVPreference {

    fun getInt(key: String): Int
    fun putInt(key: String, value: Int)

    fun getBoolean(key: String): Boolean
    fun putBoolean(key: String, value: Boolean)

    fun getString(key:String): String
    fun putString(key: String, value: String)

    enum class Tag(tag: String){
        NOTIFICATION_TAG("notificationTag"),
        SELECTED_CITY_TAG("selectedCity"),
        SELECTED_GENRE_TAG("selectedGenre"),
        USER_LOCATION_TAG("userLocation"),
        INTERACTED_ENJOYMENT("enjoymentInteraction"),
        INTERACTED_FEEDBACK("feedbackInteraction"),
        INTERACTED_RATING("ratingInteraction"),
        INTERACTION_VALUE_ENJOYMENT("enjoymentValue"),
        INTERACTION_VALUE_FEEDBACK("feedbackValue"),
        INTERACTION_VALUE_RATING("ratingValue"),
        INITIAL_TOGGLE_NOTIFICATIONS("userToggledNotifications"),
        ENABLED_NOTIFICATIONS("notificationsEnabled")
    }
}