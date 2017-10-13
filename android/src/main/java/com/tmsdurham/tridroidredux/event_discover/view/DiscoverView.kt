package com.tmsdurham.tridroidredux.event_discover.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.tmsdurham.DiscoverModel

/**
 * Created by billyparrish on 3/8/17 for Shortlist.
 */
interface DiscoverView : MvpView {
    fun renderState(state: DiscoverModel)
    fun hideCitySheet()
    fun onInitialLoad()
}