package com.tmsdurham.tridroidredux.event_discover.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ListView
import android.widget.Switch
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.squareup.moshi.Moshi
import com.tmsdurham.tridroidredux.R
import com.tmsdurham.tridroidredux.common.inject.Injector
import com.tmsdurham.tridroidredux.event_discover.epoxy.DiscoverEpoxyController
import com.tmsdurham.tridroidredux.event_discover.presenter.DiscoverPresenter
import data.KVPreference
import kotlinx.android.synthetic.main.item_city.view.*
import rx.Subscription
import com.tmsdurham.DiscoverModel
import javax.inject.Inject


class DiscoverViewImpl : MvpActivity<DiscoverView, DiscoverPresenter>(), DiscoverView {


    private val SEARCH_PERMISSIONS_REQUEST_LOCATION: Int = 99

    private val BUNDLE_RECYCLER_LAYOUT = javaClass.simpleName
    lateinit @Inject
    var discoverPresenter: DiscoverPresenter

    lateinit @Inject
    var preference: KVPreference
    lateinit @Inject
    var moshi: Moshi
    private var cityClickSubscription: Subscription? = null
    private var genreClickSubscription: Subscription? = null
    private lateinit var cityDialog: BottomSheetDialog
    private lateinit var cityList: ListView
    private var locationSwitch: Switch? = null
    private var controller: DiscoverEpoxyController? = null
    lateinit var recyclerView: RecyclerView


    private val discoverCallback: DiscoverCallbacks = object : DiscoverCallbacks {

        override fun onLocationTextClicked() {
            cityDialog.show()
        }

        override fun onFilterExpandTextClicked() {
            presenter.model.dispatch(DiscoverModel.Action.GenreDrawerToggled())
        }

        override fun onTryAgainClicked() {
            presenter.fetchEvents()
        }

        /**
         * @param genre - The String representation of the genre button that was clicked
         * @param selected - the existing current state of the genre button that was clicked,  it is expected
         * that this boolean value will be toggled elsewhere, and this function simply forwards the existing info
         * of the genre that was clicked.
         */
        override fun onGenrePillClicked(genre: kotlin.String, selected: kotlin.Boolean) {
            presenter.model.dispatch(DiscoverModel.Action.GenreTapped(genre, selected))
        }
    }

    override fun onInitialLoad() {
        presenter.loadSearchState(preference)
    }





    override fun hideCitySheet() {
        recyclerView.postDelayed({
            cityDialog.dismiss()
        }, 300)

    }


    override fun onStop() {
        super.onStop()
        presenter.saveSearchState(preference)
    }

    @SuppressLint("InflateParams")
    private fun setupSheets() {
        cityDialog = BottomSheetDialog(this)
        val cityView = layoutInflater.inflate(R.layout.sheet_city_selection, null)
        cityDialog.setContentView(cityView)
    }

    private fun setupRecyclerView() {
        controller = DiscoverEpoxyController(discoverCallback)
        controller?.let {
            recyclerView = findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView
            val layoutManager = LinearLayoutManager(this)
            it.spanCount = 1
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = it.adapter
            it.setData(presenter.model.state)
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        SEARCH_PERMISSIONS_REQUEST_LOCATION)
            }
        }
    }


    private fun hasLocationPermission(): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }




    override fun createPresenter(): DiscoverPresenter = discoverPresenter

    override fun renderState(model: DiscoverModel) = with(model.state) {
        runOnUiThread {
            locationSwitch?.isChecked = isUserLocationEnabled
            controller?.setData(this)
        }
    }


    override fun onPause() {
        super.onPause()
        cityClickSubscription?.unsubscribe()
        genreClickSubscription?.unsubscribe()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.layoutManager.onSaveInstanceState())
    }


    private fun setupCityList(): ListView {
        cityList = cityDialog.findViewById<View>(R.id.lv_city) as ListView
        cityList.adapter = CityAdapter(this, moshi)


        cityList.setOnItemClickListener { _, view, position, _ ->
            val cityAdapter = cityList.adapter as CityAdapter
            if (!view.rb_city.isChecked) {
                cityAdapter.setChecked(cityAdapter.getItem(position), !view.rb_city.isChecked) // toggle ui element
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                presenter.onCitySelected(!view.rb_city.isChecked, cityAdapter.getItem(position)) // update state
                cityAdapter.notifyDataSetChanged()
            }

        }
        return cityList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_carousel_search)
        setupRecyclerView()
        setupSheets()
        setupCityList()
        onInitialLoad()

        locationSwitch?.setOnCheckedChangeListener { compoundButton, enabled ->
            val granted = hasLocationPermission()
            if (granted) {
                presenter.onLocationToggled(enabled)
            } else if (!granted && enabled) {
                requestLocationPermission()
            }
        }
    }


    override fun onResume() {
        setupFullScreen()
        super.onResume()
    }

    private fun setupFullScreen() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }


    interface DiscoverCallbacks {
        fun onFilterExpandTextClicked()
        fun onGenrePillClicked(genre: String, selected: Boolean)
        fun onLocationTextClicked()
        fun onTryAgainClicked()
    }

}



