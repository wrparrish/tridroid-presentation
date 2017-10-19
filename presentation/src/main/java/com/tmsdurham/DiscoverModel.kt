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


class DiscoverModel @Inject constructor(@Named("cityMap") private val cityMap: MutableMap<ShortListCity, Boolean>) : StoreModel<DiscoverModel.State>() {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overrides


    override fun createStore(): Store<State> {
        return redux.createStore(
                reducer(),
                State())
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // State

    data class State(
            var cityMap: HashMap<ShortListCity, Boolean> = HashMap(),
            val city: ShortListCity = ShortListCity("New York, NY", 40.72110286598638, -73.89758759814248, 12.0),
            val genreUnfilteredEventList: List<Event> = mutableListOf(),
            val isLoading: Boolean = false,
            val isInErrorState: Boolean = false,
            val isRefreshRequired: Boolean = false,
            val isGenreDrawerVisible: Boolean = false,
            val progressString: String = "")


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Actions


    sealed class Action {
        class Initialize(val cityMap: MutableMap<ShortListCity, Boolean>)
        class SetLoading(val isLoading: Boolean)
        class IsRefreshRequired(val isRequired: Boolean)
        class CityChosen(val city: ShortListCity, val enabled: Boolean, val isUsersLocationCity: Boolean)
        class UnfilteredEventsAvailable(val events: List<Event>)
        class SearchFailure
        class NoEventsReturned
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
            is Action.SearchFailure -> state.copy(isLoading = false, isInErrorState = true)
            is Action.NoEventsReturned -> handleNoEventsReturned(state)
            is Action.UnfilteredEventsAvailable -> state.copy(genreUnfilteredEventList = action.events, progressString = "")
            is Action.CityChosen -> {
                if (action.city == state.city) {
                    state
                } else {
                    handleCitySelection(action.city, action.enabled)
                    state.copy(city = action.city, isRefreshRequired = true)
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


    private fun handleNoEventsReturned(state: State): State {
        return state.copy(isLoading = false,
                isInErrorState = true,
                genreUnfilteredEventList = mutableListOf())
    }


    private fun handleRefresh(state: State, action: Action.IsRefreshRequired): State {
        return if (action.isRequired) {
            state.copy(isRefreshRequired = action.isRequired,
                    isGenreDrawerVisible = false,
                    genreUnfilteredEventList = mutableListOf())
        } else {
            state.copy(isRefreshRequired = action.isRequired, isGenreDrawerVisible = false)
        }
    }


    fun cityStateChange(selected: Boolean, city: ShortListCity) {
        for ((key) in cityMap) {
            if (key == city) {
                cityMap[key] = selected
            }
        }

        dispatch(Action.CityChosen(city, selected, isUsersLocationCity = false))
    }


    fun provideCityMap(): MutableMap<ShortListCity, Boolean> = cityMap

}