package de.neonew.mesh.android

import android.content.Context
import android.preference.PreferenceManager

class Settings {

    companion object {
        val ONBOARDING_COMPLETED = "onboardingCompleted"
        val SSID = "ssid"
        val FREQUENCY = "frequency"
        val BSSID = "bssid"
        val IP = "ip"
        val SUBNETMASK = "subnetmask"

        fun isOnboardingCompleted(context: Context): Boolean {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getBoolean(ONBOARDING_COMPLETED, false)
        }

        fun onboardingCompleted(context: Context) {
            val sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            sharedPreferencesEditor.putBoolean(ONBOARDING_COMPLETED, true)
            sharedPreferencesEditor.apply()
        }

        fun getSsid(context: Context): String = getString(context, SSID)
        fun getFrequency(context: Context): String = getString(context, FREQUENCY)
        fun getBssid(context: Context): String = getString(context, BSSID)
        fun getIP(context: Context): String = getString(context, IP)
        fun getSubnetmask(context: Context): String = getString(context, SUBNETMASK)

        fun setSsid(context: Context, ssid: String) = setString(context, SSID, ssid)
        fun setFrequency(context: Context, frequency: String) = setString(context, FREQUENCY, frequency)
        fun setBssid(context: Context, bssid: String) = setString(context, BSSID, bssid)
        fun setIP(context: Context, ip: String) = setString(context, IP, ip)
        fun setSubnetmask(context: Context, subnetmask: String) = setString(context, SUBNETMASK, subnetmask)

        private fun getString(context: Context, key: String): String {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString(key, "")
        }

        private fun setString(context: Context, key: String, value: String) {
            val sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            sharedPreferencesEditor.putString(key, value)
            sharedPreferencesEditor.apply()
        }
    }
}
