package domain_model.top_picks

import java.io.Serializable

data class Area(val id: Int, val name: String, val description: String) : Serializable

data class Charge(val reason: String, val type: String, val amount: Double) : Serializable

data class EmbeddedPick(val offer: List<Offer>) : Serializable

data class Offer(val offerId: String,
                 val name: String,
                 val ticketTypeId: String,
                 val priceLevelId: String,
                 val description: String,
                 val currency: String,
                 val faceValue: Double,
                 val totalPrice: Double,
                 val charges: List<Charge>) : Serializable

data class Pick(
        val type: String,
        val quality: Double,
        val section: String,
        val selection: String,
        val row: String?,
        val descriptions: List<String>,
        val area: Area,
        val snapshotImageUrl: String,
        val offers: List<String>) : Serializable

data class TopPickResponse(val picks: List<Pick>, val _embedded: EmbeddedPick) : Serializable



