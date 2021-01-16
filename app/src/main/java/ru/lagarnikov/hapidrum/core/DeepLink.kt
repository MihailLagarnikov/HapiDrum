package ru.lagarnikov.hapidrum.core

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import ru.lagarnikov.hapidrum.R
import java.util.*
import kotlin.collections.ArrayList

fun getFragmentIdFromLink(link: Uri?, context: Context): Int? {
    val page = link?.lastPathSegment
    page?.run {
        return when {
            getAllLocaleString(
                R.string.instrument_main,
                context
            ).contains(page) -> R.id.mainInstrument
            getAllLocaleString(R.string.instrument_tree, context).contains(page) -> R.id.tree
            else -> null
        }
    }
    return null
}

fun getAllLocaleString(resId: Int, context: Context): ArrayList<String> {
    val rezult = ArrayList<String>()
    var conf: Configuration =
        context.getResources().getConfiguration()
    conf = Configuration(conf)

    conf.setLocale(Locale.ENGLISH)
    rezult.add(context.createConfigurationContext(conf).resources.getString(resId))

    conf.setLocale(Locale("RU"))
    rezult.add(context.createConfigurationContext(conf).resources.getString(resId))
    return rezult
}