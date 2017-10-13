package com.tmsdurham.data.preference

import android.content.SharedPreferences
import data.KVPreference

/**
 * Created by billyparrish on 5/22/17 for Shortlist.
 */
class SimplePreference(private val preference: SharedPreferences): KVPreference {
    override fun getInt(key: String): Int = preference.getInt(key, -1)

    override fun putInt(key: String, value: Int) = preference.edit().putInt(key, value).apply()

    override fun getBoolean(key: String): Boolean = preference.getBoolean(key, false)

    override fun putBoolean(key: String, value: Boolean) = preference.edit().putBoolean(key, value).apply()

    override fun getString(key: String): String = preference.getString(key, "")

    override fun putString(key: String, value: String) = preference.edit().putString(key, value).apply()
}

