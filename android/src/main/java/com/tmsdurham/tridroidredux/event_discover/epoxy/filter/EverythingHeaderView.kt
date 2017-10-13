package com.tmsdurham.tridroidredux.event_discover.epoxy.filter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.jakewharton.rxbinding.view.clickable
import com.tmsdurham.tridroidredux.R
import kotlinx.android.synthetic.main.view_header_everything.view.*

/**
 * Created by billyparrish on 10/12/17.
 */
@ModelView(defaultLayout = R.layout.everything_header_view)
class EverythingHeaderView : LinearLayout {
    lateinit var title: TextView
    lateinit var filter: TextView

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
        val view = View.inflate(context, R.layout.view_header_everything, this)
        title = view.everything_title_text
        filter = view.everything_filter_text
        filter.clickable()
    }

    @ModelProp(options = arrayOf(ModelProp.Option.GenerateStringOverloads))
    fun setTitle(title: CharSequence) {
        this.title.text = title
    }

    @ModelProp(options = arrayOf(ModelProp.Option.GenerateStringOverloads))
    fun setFilterText(caption: CharSequence) {
        this.filter.text = caption
    }

    @ModelProp(options = arrayOf(ModelProp.Option.DoNotHash))
    fun setListener(listener: View.OnClickListener) {
        this.filter.setOnClickListener(listener)
    }
}
