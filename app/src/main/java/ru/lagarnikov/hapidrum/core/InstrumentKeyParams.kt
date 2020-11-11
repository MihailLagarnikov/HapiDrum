package ru.lagarnikov.hapidrum.core

import android.widget.Button
import android.widget.ImageView
import ru.lagarnikov.hapidrum.soundlayer.KeyValues

data class InstrumentKeyParams(
    val keyParams: KeyValues,
    val button: Button,
    val animView: ImageView,
    val fileName: String
) {
}