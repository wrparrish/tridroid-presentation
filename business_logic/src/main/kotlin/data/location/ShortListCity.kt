package data.location

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Created by billyparrish on 5/8/17 for Shortlist.
 */
data class ShortListCity(val name: String, val latitude: Double, val longitude: Double, val radius: Double) {

    companion object {
        fun fromJson(json: String, moshi: Moshi): List<ShortListCity> {
            val type: Type = Types.newParameterizedType(List::class.java, ShortListCity::class.java)
            val adapter: JsonAdapter<List<ShortListCity>> = moshi.adapter(type)
            val cities: List<ShortListCity>? = adapter.fromJson(json)
            return cities ?: ArrayList()
        }
    }

}

