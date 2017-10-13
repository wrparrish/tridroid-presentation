package com.tmsdurham.tridroidredux.event_discover.epoxy.banners

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.jakewharton.rxbinding.view.clicks
import com.tmsdurham.tridroidredux.event_discover.view.DiscoverViewImpl
import com.tmsdurham.tridroidredux.R
import kotlinx.android.synthetic.main.view_header.view.*
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber


@ModelView(defaultLayout = R.layout.header_view)
class HeaderView : LinearLayout {
    lateinit var title: TextView
    lateinit var caption: TextView
    lateinit var cb: DiscoverViewImpl.DiscoverCallbacks
    var clickSubscription: Subscription? = null

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
        val view = View.inflate(context, R.layout.view_header, this)
        title = view.title_text
        caption = view.caption_text

        clickSubscription =
                Observable.merge(
                        title.clicks(),
                        caption.clicks(),
                        iv_location_pin.clicks())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            cb.onLocationTextClicked()
                        },
                                {
                                    Timber.e("OnError from clickSubscription")
                                })
    }

    @ModelProp(options = arrayOf(ModelProp.Option.GenerateStringOverloads))
    fun setTitle(title: CharSequence) {
        this.title.text = title
    }

    @ModelProp(options = arrayOf(ModelProp.Option.GenerateStringOverloads))
    fun setCaption(caption: CharSequence) {
        this.caption!!.text = caption
    }

    @ModelProp(options = arrayOf(ModelProp.Option.DoNotHash))
    fun setCallback(callback: DiscoverViewImpl.DiscoverCallbacks) {
        cb = callback
    }
}
