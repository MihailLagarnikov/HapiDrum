package ru.lagarnikov.hapidrum.core.fon_repositoriy

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.lagarnikov.hapidrum.R

class FonHolder {

    private val START_POSITION = -1
    val listFon = Fons.values()
    private var position = START_POSITION
    private var isPress = false

    fun pressFonImage(view: ImageView): Boolean {
        isPress = !isPress
        if (isPress) {
            setFonFor(view)
        } else {
            removeFonFor(view)
        }
        return isPress
    }

    fun nextTrack(view: ImageView) {
        position++
        if (position >= listFon.size) {
            position = 0
        }
        setFonFor(view)
    }

    fun previusTrack(view: ImageView) {
        position--
        if (position < 0) {
            position = listFon.size - 1
        }
        setFonFor(view)
    }

    private fun setFonFor(view: ImageView) {
        Glide.with(view).load(getDrawableFon()).into(view)
    }

    private fun removeFonFor(view: ImageView) {
        Glide.with(view).load(R.drawable.ic_start_fon).into(view)
        position = START_POSITION
    }

    fun getFonName(isDay: Boolean): Int {
        return if (position == START_POSITION) {
            R.string.nothing
        } else if (isDay) {
            listFon.get(position).param.nameDay
        } else {
            listFon.get(position).param.nameNight
        }
    }


    private fun getDrawableFon(): Int {
        position++
        if (position >= listFon.size) {
            position = 0
        }
        return listFon.get(position).param.drawable
    }
}