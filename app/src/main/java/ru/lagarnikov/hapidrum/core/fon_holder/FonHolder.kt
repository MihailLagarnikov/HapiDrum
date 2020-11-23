package ru.lagarnikov.hapidrum.core.fon_holder

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.IS_NIGHT_THEME
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import ru.lagarnikov.hapidrum.R

class FonHolder(val sharedPrefHelper: ISharedPrefHelper) : IFonHolder {

    private val START_POSITION = -1
    val listFon = Fons.values()
    private var position = START_POSITION
    private var isPress = false

    override fun pressFonImage(view: ImageView): Boolean {
        isPress = !isPress
        if (isPress) {
            setFonFor(view)
        } else {
            removeFonFor(view)
        }
        return isPress
    }

    override fun nextTrack(view: ImageView) {
        position++
        if (position >= listFon.size) {
            position = 0
        }
        setFonFor(view)
    }

    override fun previusTrack(view: ImageView) {
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

    override fun getFonName(): Int {
        return if (position == START_POSITION) {
            R.string.nothing
        } else if (sharedPrefHelper.loadBoolean(IS_NIGHT_THEME, false)) {
            listFon.get(position).param.nameNight
        } else {
            listFon.get(position).param.nameDay
        }
    }


    private fun getDrawableFon(): Int {
        position++
        if (position >= listFon.size) {
            position = 0
        }
        return if (sharedPrefHelper.loadBoolean(IS_NIGHT_THEME, false)) {
            listFon.get(position).param.drawableNight
        } else {
            listFon.get(position).param.drawableDay
        }
    }
}