package com.twosmalpixels.travel_notes.core.extension


import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.lagarnikov.hapidrum.R


fun View.setVisibility(visibility: Boolean, isGone: Boolean = true) {
    this.visibility = if (visibility) {
        View.VISIBLE
    } else if (isGone) {
        View.GONE
    } else {
        View.INVISIBLE
    }
}

fun ImageView.setPress(isOn: Boolean) {
    if (isOn) {
        this.setColorFilter(
            this.context.resources.getColor(R.color.pressed_color),
            PorterDuff.Mode.SRC_IN
        )
    } else {
        this.setColorFilter(null)
    }
}

fun TextView.setPress(isOn: Boolean) {
    if (isOn) {
        this.setTextColor(this.context.resources.getColor(R.color.pressed_color))
    } else {
        this.setTextColor(this.context.resources.getColor(R.color.white))
    }
}

fun TextView.setDisabled(isDisabled: Boolean) {
    if (isDisabled) {
        this.setTextColor(this.context.resources.getColor(R.color.white_disabled))
    } else {
        this.setTextColor(this.context.resources.getColor(R.color.white))
    }
}

fun ImageView.setDisabled(isDisabled: Boolean) {
    if (isDisabled) {
        this.setColorFilter(
            this.context.resources.getColor(R.color.light_black),
            PorterDuff.Mode.SRC_IN
        )
    } else {
        this.setColorFilter(null)
    }
}
