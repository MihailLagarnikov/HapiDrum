package ru.lagarnikov.hapidrum.core

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView

class AnimTouch() {
    private val DURATION_ALFA = 100L
    private val MAX_ALFA = 1F

    private val DURATION_HANDLE = 2000L
    private val MAX_SIZE_HANDLE = 4F
    private val NORM_SIZE_HANDLE = 1F
    private val COUNT_HANDLE = 2

    private var imageAnim: ImageView? = null
    private var animStop: ObjectAnimator? = null

    fun startAnimSound(imageAnim: ImageView) {
        this.imageAnim = imageAnim

        imageAnim.alpha = MAX_ALFA
        imageAnim.visibility = View.VISIBLE

        animStop = ObjectAnimator.ofFloat(imageAnim, View.ALPHA, MAX_ALFA, 0f)
        animStop?.duration = DURATION_ALFA
    }

    fun stopAnimSound() {
        if (imageAnim != null) {
            animStop?.start()
        }
    }

    fun animHandleStopIcon(imageView: ImageView) {

    }

}