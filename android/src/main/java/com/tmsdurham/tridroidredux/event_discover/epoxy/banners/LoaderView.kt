package com.tmsdurham.tridroidredux.event_discover.epoxy.banners

import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.tmsdurham.tridroidredux.R

/**
 * Created by billyparrish on 8/10/17 for Shortlist-Android.
 */

@EpoxyModelClass
abstract class LoaderView : EpoxyModel<LinearLayout>() {
    @EpoxyAttribute
    var progressString: String = ""



    override fun bind(view: LinearLayout) {
        val progressText = view.findViewById<TextView>(R.id.tvProgress)
        progressText.text = progressString
    }

    override fun getDefaultLayout(): Int {
        return R.layout.loader_view
    }


}
