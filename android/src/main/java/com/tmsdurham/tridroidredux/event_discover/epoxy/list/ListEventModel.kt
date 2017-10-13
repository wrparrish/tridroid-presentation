package com.tmsdurham.tridroidredux.event_discover.epoxy.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.squareup.picasso.Picasso
import com.tmsdurham.tridroidredux.common.KotlinBridge
import com.tmsdurham.tridroidredux.R
import domain_model.discover.Event
import domain_model.discover.Image
import getClosestImage
import getVenue
import kotlinx.android.synthetic.main.model_list_event.view.*

/**
 * Created by alfredovelasco on 7/20/17.
 * This model has the same data as CarouselEventModel, but has a different layout
 */
@EpoxyModelClass(layout = R.layout.model_list_event)
abstract class ListEventModel : EpoxyModelWithHolder<ListEventModel.EventHolder>() {
    @EpoxyAttribute
    var event: Event? = null
    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener = View.OnClickListener { }

    override fun bind(holder: EventHolder) {
        Picasso.with(holder.cardView.context)
                .load(imageCheck(holder.image)?.url)
                .fit()
                .centerCrop()
                .into(holder.image)
        holder.artist.text = event?.name ?: ""
        holder.dateTime.text = KotlinBridge.time(event)
        holder.venue.text = event?.getVenue()?.name ?: ""
        holder.price.text = buildPriceString(event)
        holder.cardView.setOnClickListener(clickListener)
    }

    override fun unbind(holder: EventHolder?) {
        holder?.image?.background = null
    }

    private fun imageCheck(imageView: ImageView): Image? {
        return if (imageView.width <= 200 || imageView.height <= 200) {
            event?.getClosestImage(600, 1000)
        } else {
            event?.getClosestImage(imageView.height, imageView.width)
        }
    }


    private fun buildPriceString(event: Event?): CharSequence? {
        event?.let {
            return if (it.priceRanges.isEmpty()) {
                ""
            } else {
                val min = event.priceRanges[0].min.toInt()
                val max = event.priceRanges[0].max.toInt()
                if (min == max) {
                    "$$min"
                } else {
                    "$$min-$$max"
                }
            }

        }
        return ""
    }


    inner class EventHolder : EpoxyHolder() {
        lateinit var cardView: View
        lateinit var image: ImageView
        lateinit var artist: TextView
        lateinit var dateTime: TextView
        lateinit var venue: TextView
        lateinit var price: TextView

        override fun bindView(view: View) {
            cardView = view.card_view
            image = view.event_image
            artist = view.event_artist
            dateTime = view.event_date_time
            venue = view.event_venue
            price = view.event_price
        }
    }
}
