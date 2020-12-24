package ru.lagarnikov.hapidrum.core.fon_holder

import android.widget.ImageView

interface IFonHolder {

    fun pressFonImageSwitch(view: ImageView): Boolean
    fun nextTrack(view: ImageView)
    fun previusTrack(view: ImageView)
    fun getFonName(): Int
}