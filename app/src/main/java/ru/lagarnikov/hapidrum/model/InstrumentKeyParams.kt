package ru.lagarnikov.hapidrum.model

import android.widget.Button
import android.widget.ImageView
import ru.lagarnikov.hapidrum.core.soundlayer.KeyValues

data class InstrumentKeyParams(
    val keyParams: KeyValues,
    val button: Button,
    val animView: ImageView,
    val fileName: String
) {
}