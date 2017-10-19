package com.tmsdurham.tridroidredux.event_discover


import data.location.ShortListCity

/**
 * Created by billyparrish on 5/26/17 for Shortlist.
 */

val cityMap: HashMap<ShortListCity, Boolean> = createCityMap()

fun createCityMap(): HashMap<ShortListCity, Boolean> {
val nyc  = ShortListCity(name = "New York, NY", radius = 12.65152529440462, latitude =40.72110286598638 , longitude =-73.89758759814248 )
val la = ShortListCity(name = "Los Angeles, CA", radius =46.16539413085751, latitude =36.210692, longitude =   -115.179365)


    return mapOf(nyc to true, la to false) as HashMap<ShortListCity, Boolean>
}
