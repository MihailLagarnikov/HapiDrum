package ru.lagarnikov.hapidrum.ui.main_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_instrument_fragment.*
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.RandomPlayer
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import ru.lagarnikov.hapidrum.core.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.core.fon_repositoriy.FonHolder


class MainFragment : Fragment() {

    private val fonHolder = FonHolder()
    private lateinit var loopPlayer: LoopPlayer
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private var isTopPanelHide = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentViewModel =
            ViewModelProviders.of(requireActivity()).get(MainFragmentViewModel::class.java)
        loopPlayer = LoopPlayer(requireContext())
        loadMusicFon()
        /*loadRandomgenerator()*/
        createInstrumentChangeObserver()
        createVisiblNavButtonObserver()
        createTopPanel()
        initTopPanel()
    }

    private fun createTopPanel(){

    }

    private fun createInstrumentChangeObserver() {
        mainFragmentViewModel.currentInstrumentKeyParamsList.observe(this, Observer {
            if (mainFragmentViewModel.isCurrentInstrumentLoaded) {
                loadSamples(it)
            }
        })
    }

    private fun createVisiblNavButtonObserver() {
        val TRANSLATE_LEFT_NORMAL = resources.getDimension(R.dimen.padding_navigation_normal)
        val TRANSLATE_LEFT_OUT = -resources.getDimension(R.dimen.padding_navigation_out)
        val TRANSLATE_RIGHT_NORMAL = -resources.getDimension(R.dimen.padding_navigation_normal)
        val TRANSLATE_RIGHT_OUT = resources.getDimension(R.dimen.padding_navigation_out)
        mainFragmentViewModel.visibilityNavigButton.observe(this, Observer {
            translater(btn_left_instrument, if (it) TRANSLATE_LEFT_NORMAL else TRANSLATE_LEFT_OUT)
            translater(
                btn_right_instrument,
                if (it) TRANSLATE_RIGHT_NORMAL else TRANSLATE_RIGHT_OUT
            )
        })
    }

    private fun translater(view: View, value: Float) {
        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, value)
        animator.repeatCount = 0
        animator.duration = resources.getInteger(R.integer.anim_duration_small).toLong()
        animator.repeatMode = ObjectAnimator.RESTART
        animator.start()
    }

    private fun loadMusicFon() {
        image_music_fon.setOnClickListener {
            (it as ImageView)
                .setImageResource(
                    if (MyMediaPlayer(requireContext()).startMusick()) R.drawable.btn_music_fon
                    else R.drawable.btn_music_fon_off
                )
        }
    }

    private fun loadSamples(instrumentKeyParamsList: ArrayList<InstrumentKeyParams>) {
        for (params in instrumentKeyParamsList) {
            loopPlayer.setInstrumentParamsKey(params)
        }

        stop_all.setOnClickListener { loopPlayer.stopAllSounds() }
        image_fon.setOnClickListener { fonHolder.setFonFor(fon_image) }
    }

    private fun loadRandomgenerator() {
        val randomGenerator = RandomPlayer(
            loopPlayer,
            button_a,
            button_b,
            button_c,
            button_d,
            button_e,
            button_f,
            button_g,
            button_h, button_zero
        )
        image_random.setOnClickListener {
            image_random.setImageDrawable(
                resources.getDrawable(if (randomGenerator.pressGenerator()) R.drawable.auto_pressed else R.drawable.auto)
            )
        }
    }

    private fun initTopPanel() {
        hide_show_top_panel_button.setOnClickListener {
            if (isTopPanelHide) {
                motion_layout_top_panel.transitionToEnd()
            } else {
                motion_layout_top_panel.transitionToStart()
            }
        }
        motion_layout_top_panel.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(
                p0: MotionLayout?,
                p1: Int,
                p2: Boolean,
                p3: Float
            ) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(
                p0: MotionLayout?,
                p1: Int,
                p2: Int,
                p3: Float
            ) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                isTopPanelHide = p1 == R.id.start
                hide_show_top_panel_button.setImageResource(getTopPanelButtonImage(isTopPanelHide))

            }
        })
    }

    private fun getTopPanelButtonImage(isTop: Boolean): Int {
        return if (isTop) {
            R.drawable.ic_top_panel_to_down
        } else {
            R.drawable.ic_top_panel_to_top
        }
    }
}