package com.tmsdurham.tridroidredux.event_discover.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ListView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.squareup.moshi.Moshi
import com.tmsdurham.DiscoverModel
import com.tmsdurham.tridroidredux.R
import com.tmsdurham.tridroidredux.common.inject.Injector
import com.tmsdurham.tridroidredux.event_discover.epoxy.DiscoverEpoxyController
import com.tmsdurham.tridroidredux.event_discover.presenter.DiscoverPresenter
import data.KVPreference
import kotlinx.android.synthetic.main.item_city.view.*
import rx.Subscription
import javax.inject.Inject


class DiscoverViewImpl : MvpActivity<DiscoverView, DiscoverPresenter>(), DiscoverView {



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
    private var controller: DiscoverEpoxyController? = null
    lateinit var recyclerView: RecyclerView


    private val discoverCallback: DiscoverCallbacks = object : DiscoverCallbacks {

        override fun onLocationTextClicked() {
            cityDialog.show()
        }

        override fun onTryAgainClicked() {
            presenter.fetchEvents()
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

    override fun createPresenter(): DiscoverPresenter = discoverPresenter

    override fun renderState(model: DiscoverModel) = with(model.state) {
        runOnUiThread {
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
        fun onLocationTextClicked()
        fun onTryAgainClicked()
    }

}



