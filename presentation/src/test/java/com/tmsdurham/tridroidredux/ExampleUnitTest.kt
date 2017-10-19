package com.tmsdurham.tridroidredux

import com.tmsdurham.DiscoverModel
import data.location.ShortListCity
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val model: DiscoverModel = DiscoverModel(cityMap = mutableMapOf())


    @Test
    fun itShouldInitializeState() {
        val givenState = model.state
        val givenCityMap = givenState.cityMap

        model.dispatch(DiscoverModel.Action.Initialize(createCityMap()))

        val statefulCityMap = model.state.cityMap
        assertNotEquals(givenCityMap, statefulCityMap)
    }


    fun createCityMap(): HashMap<ShortListCity, Boolean> {
        val nyc = ShortListCity(name = "New York, NY", radius = 12.65152529440462, latitude = 40.72110286598638, longitude = -73.89758759814248)
        val la = ShortListCity(name = "Los Angeles, CA", radius = 46.16539413085751, latitude = 36.210692, longitude = -115.179365)

        return mapOf(nyc to true, la to false) as HashMap<ShortListCity, Boolean>
    }

}