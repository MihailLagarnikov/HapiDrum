package ru.lagarnikov.hapidrum.ui.instruments.tree


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.tree_fragment.*
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.base_fragment.BaseInstrumentFragment
import ru.lagarnikov.hapidrum.core.sound_loader.MAIN_
import ru.lagarnikov.hapidrum.core.sound_loader.MAIN_SOUNDS_
import ru.lagarnikov.hapidrum.core.sound_player.KeyValues
import ru.lagarnikov.hapidrum.model.AditionalInstrumentInfo
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import ru.lagarnikov.hapidrum.ui.main_fragment.MainFragmentViewModel

class Tree : BaseInstrumentFragment() {

    private val MAX_SNOWFLAKE = 50
    private lateinit var star: ImageView

    override fun getLayout() = R.layout.tree_fragment

    override fun getInstrumentName() = MAIN_

    override fun getInstrumentNameForAnalytic() = R.string.instrument_main

    override fun getInstrumentAboutData(): InstrumentAboutData {
        return return InstrumentAboutData(
            getString(R.string.instrument_main),
            getString(R.string.shop_2smallPixels),
            getString(R.string.url_kosmosky),
            arrayListOf(
                AditionalInstrumentInfo(
                    getString(R.string.diameter_title),
                    getString(R.string.instrument_tree)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.weight_title),
                    getString(R.string.empty)
                )
            ),
            false,
            getString(R.string.instrument_tree),
            getString(R.string.shop_title_2smallPixels),
            getString(R.string.instrument),
            getString(R.string.weight_title_2smallPixels)
        )
    }


    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        return arrayListOf(
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                getFilePath(MAIN_SOUNDS_.get(0).fileLocalName, MAIN_SOUNDS_.get(0).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(MAIN_SOUNDS_.get(1).fileLocalName, MAIN_SOUNDS_.get(1).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(MAIN_SOUNDS_.get(2).fileLocalName, MAIN_SOUNDS_.get(2).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(MAIN_SOUNDS_.get(3).fileLocalName, MAIN_SOUNDS_.get(3).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(MAIN_SOUNDS_.get(4).fileLocalName, MAIN_SOUNDS_.get(4).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(MAIN_SOUNDS_.get(5).fileLocalName, MAIN_SOUNDS_.get(5).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(MAIN_SOUNDS_.get(6).fileLocalName, MAIN_SOUNDS_.get(6).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(MAIN_SOUNDS_.get(7).fileLocalName, MAIN_SOUNDS_.get(7).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.ZERO,
                button_zero,
                hapi_drum_zero,
                getFilePath(MAIN_SOUNDS_.get(8).fileLocalName, MAIN_SOUNDS_.get(8).fileExtension)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        star = requireView().findViewById(R.id.star);
        val mainFragmentViewModel =
            ViewModelProviders.of(requireActivity()).get(MainFragmentViewModel::class.java)
        mainFragmentViewModel.isKeyPressed.observe(requireActivity(), Observer {
            if (it) {
                shower()
            }

            if (it && mainFragmentViewModel.keyPressedId.value == R.id.button_d) {
                for (i in 0..MAX_SNOWFLAKE) {
                    shower()
                }
            }
        })
    }

    private fun shower() {

        // Create a new star view in a random X position above the container.
        // Make it rotateButton about its center as it falls to the bottom.

        // Local variables we'll need in the code below
        val asas = star
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()

        // Create the new star (an ImageView holding our drawable) and add it to the container
        val newStar = AppCompatImageView(requireContext())
        newStar.setImageResource(R.drawable.snowflake)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        // Scale the view randomly between 10-160% of its default size
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        // Position the view at a random place between the left and right edges of the container
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        // Create an animator that moves the view from a starting position right about the container
        // to an ending position right below the container. Set an accelerate interpolator to give
        // it a gravity/falling feel
        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        // Create an animator to rotateButton the view around its center up to three times
        val rotator = ObjectAnimator.ofFloat(
            newStar, View.ROTATION,
            (Math.random() * 1080).toFloat()
        )
        rotator.interpolator = LinearInterpolator()

        // Use an AnimatorSet to play the falling and rotating animators in parallel for a duration
        // of a half-second to two seconds
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 2500 + 2000).toLong()

        // When the animation is done, remove the created view from the container
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        // Start the animation
        set.start()
    }
}