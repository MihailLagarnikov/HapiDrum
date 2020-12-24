package com.twosmalpixels.travel_notes.core.repositoriy.SharedPref

import android.content.SharedPreferences


class SharedPrefHelper: ISharedPrefHelper {

    private lateinit var mPref: SharedPreferences

    override fun init(pref: SharedPreferences){
        this.mPref = pref
    }

    override fun saveText(key: String, value: String){
        val ed: SharedPreferences.Editor = mPref.edit()
        ed.putString(key, value)
        ed.apply()
    }

    override fun loadText(key: String, defValue: String): String{
        return mPref.getString(key, defValue) ?: defValue
    }

    override fun loadBoolean(key: String, defValue: Boolean): Boolean {
        return mPref.getBoolean(key, defValue)
    }

    override fun saveBoolean(key: String, value: Boolean) {
        val ed: SharedPreferences.Editor = mPref.edit()
        ed.putBoolean(key, value)
        ed.apply()
    }

    override fun loadLong(key: String, defValue: Long): Long {
        return mPref.getLong(key, defValue)
    }

    override fun saveLong(key: String, value: Long) {
        val ed: SharedPreferences.Editor = mPref.edit()
        ed.putLong(key, value)
        ed.apply()
    }

    override fun loadInt(key: String, defValue: Int): Int {
        return mPref.getInt(key, defValue)
    }

    override fun saveInt(key: String, value: Int) {
        val ed: SharedPreferences.Editor = mPref.edit()
        ed.putInt(key, value)
        ed.apply()
    }

    override fun loadDouble(key: String, defValue: Double): Double {
        return mPref.getFloat(key, defValue.toFloat()).toDouble()
    }

    override fun saveDouble(key: String, value: Double) {
        val ed: SharedPreferences.Editor = mPref.edit()
        ed.putFloat(key, value.toFloat())
        ed.apply()
    }
}