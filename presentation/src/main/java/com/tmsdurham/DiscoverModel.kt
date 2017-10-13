package com.tmsdurham

import StoreModel

import data.location.ShortListCity
import domain_model.discover.Event
import domain_model.discover.Location
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import redux.api.Reducer
import redux.api.Store
import redux.combineReducers
import java.lang.Math.*
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.HashMap


class DiscoverModel @Inject constructor(@Named("cityMap") val cityMap: MutableMap<ShortListCity, Boolean>) : StoreModel<DiscoverModel.State>() {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overrides


    override fun createStore(): Store<State> {
        return redux.createStore(
                combineReducers(reducer()),
                State())
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // State

    data class State(
            var genreMap: HashMap<String, Boolean> = HashMap(),
            var cityMap: HashMap<ShortListCity, Boolean> = HashMap(),
            val city: ShortListCity = ShortListCity("New York, NY", 40.688149, -73.903491, 12.0),
            val genreUnfilteredEventList: List<Event> = mutableListOf(),
            var genreFilteredEventList: List<Event> = mutableListOf(),
            val carouselEventList: List<Event> = mutableListOf(),
            val genreList: List<String> = mutableListOf(),
            val isLoading: Boolean = false,
            val isInErrorState: Boolean = false,
            val isRefreshRequired: Boolean = false,
            val isLocationLoading: Boolean = false,
            val isUserLocationEnabled: Boolean = false,
            val isGenreDrawerVisible: Boolean = false,
            val hasInteractedWithEnjoyment: Boolean = false,
            val enjoymentValue: Boolean = false,
            val hasInteractedWithFeedback: Boolean = false,
            val feedbackValue: Boolean = false,
            val hasInteractedWithRating: Boolean = false,
            val ratingValue: Boolean = false,
            val notificationsEnabled: Boolean = false,
            val progressString: String = "")


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Actions


    sealed class Action {
        class Initialize(val cityMap: MutableMap<ShortListCity, Boolean>)

        class GenreTapped(val genre: String, val enabled: Boolean)
        class SetLoading(val isLoading: Boolean)
        class IsRefreshRequired(val isRequired: Boolean)
        class CityChosen(val city: ShortListCity, val enabled: Boolean, val isUsersLocationCity: Boolean)
        class UnfilteredEventsAvailable(val events: List<Event>)
        class CarouselEventsAvailable(val events: List<Event>)
        class GenreDrawerToggled
        class UserLocationToggled(val isSelected: Boolean)
        class UserLocationResolved
        class SearchFailure
        class NoEventsReturned
        class GenreListAvailable(val genreNames: List<String>)
        class EmitProgress(val progressString: String)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Reducer

    private fun reducer() = Reducer { state: State, action: Any ->
        when (action) {
            is Action.Initialize -> state.copy(cityMap = action.cityMap as HashMap<ShortListCity, Boolean>)
            is Action.IsRefreshRequired -> handleRefresh(state, action)
            is Action.SetLoading -> state.copy(isLoading = action.isLoading, isInErrorState = if (action.isLoading) false else state.isInErrorState)
            is Action.EmitProgress -> state.copy(progressString = action.progressString)
            is Action.GenreTapped -> handleGenreTap(action, state)
            is Action.GenreDrawerToggled -> toggleDrawer(state)
            is Action.SearchFailure -> state.copy(isLoading = false, isInErrorState = true)
            is Action.NoEventsReturned -> handleNoEventsReturned(state)
            is Action.UserLocationToggled -> toggleLocation(state, action)
            is Action.UserLocationResolved -> state.copy(isLocationLoading = false, isUserLocationEnabled = true)
            is Action.CarouselEventsAvailable -> state.copy(carouselEventList = action.events)
            is Action.UnfilteredEventsAvailable -> state.copy(genreUnfilteredEventList = action.events, genreFilteredEventList = action.events, progressString = "")  // this initializes the list to include all events if user has not yet tapped a filter.
            is Action.GenreListAvailable -> buildGenreMap(action.genreNames)

            is Action.CityChosen -> {
                if (action.city == state.city) {
                    state
                } else {
                    handleCitySelection(action.city, action.enabled)
                    state.copy(city = action.city, isUserLocationEnabled = action.isUsersLocationCity, isRefreshRequired = true)
                }
            }
            else -> state
        }

    }

    private fun handleCitySelection(city: ShortListCity, isEnabled: Boolean) {
        val tempCities = HashMap<ShortListCity, Boolean>()
        cityMap.keys.forEach { tempCities.put(it, false) }
        tempCities.put(city, isEnabled)
        cityMap.clear()
        cityMap.putAll(tempCities)
    }

    private fun toggleDrawer(state: State): State? {
        if (state.isGenreDrawerVisible) {  // the drawer is currently visible, so the toggle needs to  hide it and reset the filter pills
            for ((key, _) in state.genreMap) {
                state.genreMap[key] = true
            }
            return state.copy(genreFilteredEventList = state.genreUnfilteredEventList, isGenreDrawerVisible = false)
        } else {  // we are just opening the drawer
            return state.copy(isGenreDrawerVisible = true)
        }

    }

    private fun handleNoEventsReturned(state: State): State {
        return state.copy(isLoading = false,
                isInErrorState = true,
                genreUnfilteredEventList = mutableListOf(),
                genreFilteredEventList = mutableListOf(),
                carouselEventList = mutableListOf(),
                genreMap = HashMap())
    }

    private fun buildGenreMap(genreNames: List<String>): State {
        val mapToReturn = HashMap<String, Boolean>()
        genreNames.forEach {
            mapToReturn[it] = true
        }
        return state.copy(genreMap = mapToReturn, genreList = genreNames)
    }

    private fun handleRefresh(state: State, action: Action.IsRefreshRequired): State {
        return if (action.isRequired) {
            state.copy(isRefreshRequired = action.isRequired,
                    isGenreDrawerVisible = false,
                    genreUnfilteredEventList = mutableListOf(),
                    genreFilteredEventList = mutableListOf(),
                    carouselEventList = mutableListOf(),
                    genreMap = HashMap())

        } else {
            state.copy(isRefreshRequired = action.isRequired, isGenreDrawerVisible = false)
        }
    }

    /**
     * This method received the unaltered state of the genre that was interacted with
     * we need to toggle / flip the state of it.
     */
    private fun handleGenreTap(action: Action.GenreTapped, state: State): State {
        state.genreMap[action.genre] = !action.enabled

        state.genreFilteredEventList = getFilteredList(state)
        return state
    }

    private fun getFilteredList(state: State): List<Event> {


        return state.genreUnfilteredEventList
                .filter { state.genreMap[it.classifications.first().genre.name] ?: false }
                .sortedWith(compareBy {
                    val timeString = it.dates.start.dateTime
                    val javaTimeZone = TimeZone.getTimeZone(it.dates.timezone)
                    val zone = DateTimeZone.forTimeZone(javaTimeZone)
                    val eventTime = DateTime(timeString, zone)
                    eventTime.millis
                })
    }

    private fun toggleLocation(state: State, action: Action.UserLocationToggled): State {
        if (action.isSelected) return state.copy(isLocationLoading = true)
        else return state.copy(isLocationLoading = false, isUserLocationEnabled = false)
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Epic


/*    private fun locationEpic() = Epic { actions: Observable<out Any>, store: Store<State> ->
        actions.ofType(Action.UserLocationToggled::class.java)
                .filter { it.isSelected }
                .flatMap { getLocation.execute(GetLocation.Request())
                        .onErrorReturn { null }}
                .filter { it != null }
                .map { nearestCity(it.location) }
                .filter { it != null }
                .map { Action.CityChosen(it!!, true, true) }
                .doOnNext { store.dispatch(Action.UserLocationResolved()) }
    }*/


    //////////////////////

    fun cityStateChange(selected: Boolean, city: ShortListCity) {
        for ((key) in cityMap) {
            if (key == city) {
                cityMap[key] = selected
            }
        }

        dispatch(Action.CityChosen(city, selected, isUsersLocationCity = false))
    }


    fun provideCityMap(): MutableMap<ShortListCity, Boolean> = cityMap


    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6372.8
        val λ1 = toRadians(lat1)
        val λ2 = toRadians(lat2)
        val Δλ = toRadians(lat2 - lat1)
        val Δφ = toRadians(lon2 - lon1)
        return 2 * R * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
    }

    private fun nearestCity(userLocation: Location): ShortListCity? {
        if (userLocation.latitude.isEmpty() || userLocation.longitude.isEmpty()) {
            return cityMap.keys.first { it.name == "Dallas, TX" }  // something went wrong, return 'something'
        }
        return cityMap.keys.minBy {
            haversine(userLocation.latitude.toDouble(), userLocation.longitude.toDouble(), it.latitude, it.longitude)
        }
    }


}