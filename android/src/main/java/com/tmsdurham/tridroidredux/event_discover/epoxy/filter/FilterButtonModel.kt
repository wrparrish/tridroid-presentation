package com.tmsdurham.tridroidredux.event_discover.epoxy.filter

import android.support.v7.widget.AppCompatButton
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.tmsdurham.tridroidredux.R

/**
 * Created by alfredovelasco on 7/19/17.
 */
@EpoxyModelClass(layout = R.layout.filter_button_view)
abstract class FilterButtonModel : EpoxyModelWithHolder<FilterButtonModel.FilterHolder>() {
    @EpoxyAttribute var genreName: String = ""
    @EpoxyAttribute var genreEnabled: Boolean = true
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var callback:  View.OnClickListener


    override fun bind(holder: FilterHolder?) {
        super.bind(holder)
        holder?.button?.setOnClickListener(callback)
        holder?.button?.text = genreName
        if (genreEnabled) holder?.button?.alpha = 1f else holder?.button?.alpha = .4f
    }

    class FilterHolder : EpoxyHolder() {
        lateinit var button: AppCompatButton
        override fun bindView(v: View?) {
            button = v as AppCompatButton
        }

    }

}
