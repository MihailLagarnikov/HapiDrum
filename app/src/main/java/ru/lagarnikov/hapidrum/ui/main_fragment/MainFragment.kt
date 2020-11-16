package ru.lagarnikov.hapidrum.ui.main_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_instrument_fragment.*
import kotlinx.android.synthetic.main.top_sheet_panel.*
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.TopSheetBehavior
import ru.lagarnikov.hapidrum.core.RandomPlayer
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import ru.lagarnikov.hapidrum.core.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.core.fon_repositoriy.FonHolder


class MainFragment : Fragment() {

    private val fonHolder = FonHolder()
    private lateinit var loopPlayer: LoopPlayer
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

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
    }

    private fun createTopPanel(){
        val topSheetBehavior = TopSheetBehavior.from(top_sheet_layout)
        topSheetBehavior.peekHeight = resources.getDimension(R.dimen.behavior_peek_height).toInt()
        peekContainer.setOnClickListener {
            if (topSheetBehavior.state != TopSheetBehavior.STATE_EXPANDED) {
                topSheetBehavior.state = TopSheetBehavior.STATE_EXPANDED
            } else {
                topSheetBehavior.state = TopSheetBehavior.STATE_COLLAPSED
            }
        }
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
}