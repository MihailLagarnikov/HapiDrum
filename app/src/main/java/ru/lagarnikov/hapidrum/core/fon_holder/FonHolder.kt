package ru.lagarnikov.hapidrum.core.fon_holder

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.IS_NIGHT_THEME
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import ru.lagarnikov.hapidrum.R

class FonHolder(val sharedPrefHelper: ISharedPrefHelper) : IFonHolder {

    private val START_POSITION = -1

    private val mListFon = Fons.values()
    private var mPosition = START_POSITION
    private var isPress = false

    override fun pressFonImageSwitch(view: ImageView): Boolean {
        isPress = !isPress
        if (isPress) {
            mPosition = 0
            setFonFor(view)
        } else {
            removeFonFor(view)
        }
        return isPress
    }

    override fun nextTrack(view: ImageView) {
        mPosition++
        if (mPosition >= mListFon.size) {
            mPosition = 0
        }
        setFonFor(view)
    }

    override fun previusTrack(view: ImageView) {
        mPosition--
        if (mPosition < 0) {
            mPosition = mListFon.size - 1
        }
        setFonFor(view)
    }

    private fun setFonFor(view: ImageView) {
        Glide.with(view).load(getDrawableFon()).into(view)
    }

    private fun removeFonFor(view: ImageView) {
        Glide.with(view).load(R.drawable.ic_start_fon).into(view)
        mPosition = START_POSITION
    }

    override fun getFonName(): Int {
        return if (mPosition == START_POSITION) {
            R.string.nothing
        } else if (sharedPrefHelper.loadBoolean(IS_NIGHT_THEME, false)) {
            mListFon.get(mPosition).param.nameNight
        } else {
            mListFon.get(mPosition).param.nameDay
        }
    }

    private fun getDrawableFon(): Int {
        return if (sharedPrefHelper.loadBoolean(IS_NIGHT_THEME, false)) {
            mListFon.get(mPosition).param.drawableNight
        } else {
            mListFon.get(mPosition).param.drawableDay
        }
    }
}