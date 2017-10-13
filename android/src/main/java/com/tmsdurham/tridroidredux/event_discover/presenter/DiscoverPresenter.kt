package com.tmsdurham.tridroidredux.event_discover.presenter


import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.tmsdurham.tridroidredux.common.NUM_DAYS
import com.tmsdurham.tridroidredux.common.SEARCH_SIZE
import com.tmsdurham.tridroidredux.common.getEndDateTime
import com.tmsdurham.tridroidredux.event_discover.cityMap
import com.tmsdurham.tridroidredux.event_discover.view.DiscoverView
import data.KVPreference
import domain_model.discover.Event
import domain_model.discover.InventoryStatus
import getAttraction
import getStartMillis
import redux.api.Store
import rx.Observable
import rx.Subscription
import com.tmsdurham.DiscoverModel
import com.tmsdurham.DiscoverModel.Action.*
import timber.log.Timber
import timeCheck
import undefinedGenreCheck
import use_case.event.GetEvents
import use_case.event.GetInventoryStatus
import javax.inject.Inject


class DiscoverPresenter @Inject constructor(val model: DiscoverModel,
                                            private val getEvents: GetEvents,
                                            private val getInventoryStatus: GetInventoryStatus) : MvpBasePresenter<DiscoverView>(), Store.Subscriber {

    private var subscription: Store.Subscription? = null
    private var eventFetchSubscription: Subscription? = null

    override fun onStateChanged() {

        with(model.state) {
            if (isRefreshRequired) {
                model.dispatch(DiscoverModel.Action.IsRefreshRequired(false))
                fetchEvents()
            }
        }

        view?.renderState(model)
    }


    override fun attachView(view: DiscoverView?) {
        super.attachView(view)
        subscription = model.subscribe(this)
    }


    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        subscription?.unsubscribe()
        eventFetchSubscription?.unsubscribe()
    }


    fun fetchEvents() {
        model.dispatch(SetLoading(true))
        model.dispatch(EmitProgress("Fetching Events"))

        var eventList = listOf<Event>()
        val eventIdToInventoryMap = HashMap<String, InventoryStatus>()

        val eventRequest: GetEvents.Request = GetEvents.Request(buildEventParamMap())

        eventFetchSubscription =
                getEvents.execute(eventRequest)
                        .flatMap {
                            eventList = it.events.filter { !it.getAttraction()?.name.isNullOrEmpty() }
                            var csvString = ""
                            eventList.forEach { csvString = csvString.plus("${it.id},") }
                            csvString.dropLast(1)  // removes extra ,
                            val request = GetInventoryStatus.Request(csvString)
                            model.dispatch(EmitProgress("Checking inventory for ${eventList.size} events."))
                            getInventoryStatus.execute(request)
                                    .map { it.statusArray }
                                    .map { it.forEach { eventIdToInventoryMap.put(it.eventId, it.status) } }
                        }
                        .flatMap {
                            Observable.from(eventList)
                                    .filter {
                                        val status = eventIdToInventoryMap[it.id]
                                        status != InventoryStatus.TICKETS_NOT_AVAILABLE
                                    }
                        }
                        .filter { it.timeCheck() && !it.undefinedGenreCheck() }
                        .toList()
                        .subscribe({
                            // on next
                            if (it.isEmpty()) {
                                model.dispatch(NoEventsReturned())
                            } else {
                                makeDispatchesFromEventListGeneration(it)
                            }
                        }, {
                            // on error
                            Timber.e("getEvents error -  $it")
                            model.dispatch(SearchFailure())
                        })
    }

    private fun makeDispatchesFromEventListGeneration(it: List<Event>) {

        model.dispatch(UnfilteredEventsAvailable(it.sortedBy { it.getStartMillis() }.distinctBy { it.id }))
        val genresInList: List<String> = it
                .flatMap { it.classifications }
                .map { it.genre.name }
                .distinctBy { it }
                .toMutableList()
        model.dispatch(GenreListAvailable(genresInList))
        model.dispatch(SetLoading(false))
    }


    private fun buildEventParamMap(): Map<String, String> {
        val city = model.state.city
        return mapOf(
                "latlong" to "${city.latitude},${city.longitude}",
                "radius" to "${city.radius.toInt()}",
                "view" to "internal",
                "classificationName" to "music",
                "endDateTime" to getEndDateTime(NUM_DAYS),
                "size" to SEARCH_SIZE)
    }


    fun onCitySelected(selected: Boolean, cityString: String) {
        val city = cityMap.keys.first { it.name == cityString }
        model.cityStateChange(selected, city)
        if (isViewAttached) {
            view?.hideCitySheet()
        }
    }


    fun onLocationToggled(enabled: Boolean) {
        model.dispatch(DiscoverModel.Action.UserLocationToggled(enabled))
        if (isViewAttached) {
            view?.hideCitySheet()
        }
    }

    /**
     * This method checks for a saved city name, and boolean flags for the toggle switch on the genre
     * and city sheet, and individual flags regarding the interaction state with the end view survey.
     * It retrieves the csv string of genres,   dispatches an action to set those as enabled.
     * In the future, we should prefer to take the steps to  serialize and deserialize the entire state object.
     */
    fun loadSearchState(preference: KVPreference) {
        model.dispatch(DiscoverModel.Action.Initialize(model.provideCityMap()))

        val selectedCity = preference.getString(KVPreference.Tag.SELECTED_CITY_TAG.name)
        val isUsingUsersLocation = preference.getBoolean(KVPreference.Tag.USER_LOCATION_TAG.name)

        if (selectedCity.isNotEmpty()) {
            val city = cityMap.keys.first { it.name == selectedCity }
            model.dispatch(DiscoverModel.Action.CityChosen(city, true, isUsingUsersLocation))
        } else {
            // first load scenario
            model.dispatch(DiscoverModel.Action.IsRefreshRequired(true))
        }

        if (isUsingUsersLocation) model.dispatch(DiscoverModel.Action.UserLocationResolved())
    }


    /**
     * This method saves the information required to maintain state of the users search preferences, and survey state.
     */
    fun saveSearchState(preference: KVPreference) {
        val selectedCity = model.state.city
        val isUsingUsersLocation = model.state.isUserLocationEnabled

        preference.putString(KVPreference.Tag.SELECTED_CITY_TAG.name, selectedCity.name)
        preference.putBoolean(KVPreference.Tag.USER_LOCATION_TAG.name, isUsingUsersLocation)
    }


}


