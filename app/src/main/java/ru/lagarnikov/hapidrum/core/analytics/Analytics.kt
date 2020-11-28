package ru.lagarnikov.hapidrum.core.analytics

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*

class Analytics {

    companion object {
        private var instance: FirebaseAnalytics? = null

        fun init(context: Context?) {
            if (!FirebaseApp.getApps(context!!).isEmpty() && instance == null) {
                instance = FirebaseAnalytics.getInstance(context)
            }
        }

        fun logEventPushWithParameter(
            eventName: String,
            parameter: String,
            value: String?
        ) {
            val bundle = Bundle()
            bundle.putString(parameter, value)
            logEvent(eventName, bundle)
        }

        fun logEventPushWithParameter(
            eventName: String,
            parameter: String,
            value: Int,
            context: Context
        ) {
            val bundle = Bundle()
            bundle.putString(parameter, getEnglishLocaleString(value, context))
            logEvent(eventName, bundle)
        }

        private fun logEvent(eventName: String, bundle: Bundle?) {
            instance?.logEvent(eventName, bundle)
        }

        private fun getEnglishLocaleString(resId: Int, context: Context): String? {
            var conf: Configuration =
                context.getResources().getConfiguration()
            conf = Configuration(conf)
            conf.setLocale(Locale.ENGLISH)
            val localizedContext: Context =
                context.createConfigurationContext(conf)
            return localizedContext.resources.getString(resId)
        }
    }

}