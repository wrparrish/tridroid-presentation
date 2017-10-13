package com.tmsdurham.tridroidredux.event_discover.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.squareup.moshi.Moshi
import com.tmsdurham.tridroidredux.event_discover.cityMap
import com.tmsdurham.tridroidredux.R


/**
 * Created by billyparrish on 3/11/17 for Shortlist.
 */


class CityAdapter(val context: Context, val moshi: Moshi) : BaseAdapter() {
    val cities: ArrayList<String>  =  ArrayList()

    init {
        cityMap.keys.toList().forEach {
            cities.add(it.name)
        }
        cities.sort()
    }




    override fun getCount(): Int {
        return cities.size
    }

    override fun getItem(i: Int): String {
        return cities[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View? {
        val v = LayoutInflater.from(context).inflate(R.layout.item_city, viewGroup, false)
        val cityText: TextView = v?.findViewById<TextView>(R.id.tv_city_name) as TextView
        val radio = v?.findViewById<RadioButton>(R.id.rb_city) as RadioButton
        cityText.text = getItem(position)
        radio.isChecked = isChecked(cityText.text.toString())
        return v
    }

    fun setChecked(city: String, checked: Boolean){
         for ((key) in cityMap){
             if (key.name === city){
                 cityMap[key] = checked
             }
         }

    }

    fun isChecked(city: String) :Boolean {
        var checked = false
        for ((key) in cityMap){
            if (key.name === city){
                val value = cityMap[key]
                value?.let { value ->
                    checked = value
                }

            }
        }
        return checked
    }
}