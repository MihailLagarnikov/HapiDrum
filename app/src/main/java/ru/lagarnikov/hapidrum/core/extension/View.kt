package com.twosmalpixels.travel_notes.core.extension


import android.view.View


fun View.setVisibility(visibility: Boolean, isGone: Boolean = true){
    this.visibility = if (visibility){
        View.VISIBLE
    }else if (isGone){
        View.GONE
    }else{
        View.INVISIBLE
    }
}
