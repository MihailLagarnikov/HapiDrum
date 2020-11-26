package ru.lagarnikov.hapidrum.model

import android.widget.Button
import android.widget.ImageView
import ru.lagarnikov.hapidrum.core.sound_player.KeyValues

data class InstrumentKeyParams(
    val keyParams: KeyValues,
    val button: Button,
    val animView: ImageView,
    val fileName: String
) {
}