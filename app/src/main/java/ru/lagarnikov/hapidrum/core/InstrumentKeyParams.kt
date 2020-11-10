package ru.lagarnikov.hapidrum.core

import android.widget.Button
import android.widget.ImageView
import ru.lagarnikov.hapidrum.soundlayer.Sounds

data class InstrumentKeyParams(
    val keyParams: Sounds,
    val button: Button,
    val animView: ImageView,
    val fileName: String
) {
}