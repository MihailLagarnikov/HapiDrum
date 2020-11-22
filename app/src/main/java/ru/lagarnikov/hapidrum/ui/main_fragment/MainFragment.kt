package ru.lagarnikov.hapidrum.ui.main_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.twosmalpixels.travel_notes.core.extension.setDisabled
import com.twosmalpixels.travel_notes.core.extension.setPress
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_instrument_fragment.*
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.RandomGenerator
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import ru.lagarnikov.hapidrum.core.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.core.fon_repositoriy.FonHolder
import ru.lagarnikov.hapidrum.core.sound_loader.SoundFons


class MainFragment : Fragment() {

    private val sharedPref: ISharedPrefHelper by KoinJavaComponent.inject(ISharedPrefHelper::class.java)
    private val fonHolder = FonHolder()
    private lateinit var loopPlayer: LoopPlayer
    private lateinit var fonPlayer: MyMediaPlayer
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private var randomGenerator: RandomGenerator? = null
    private var isTopPanelHide = true
    private var isRandomOn = false
    private var isFonOn = false

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
        fonPlayer = MyMediaPlayer(requireContext())
        createInstrumentChangeObserver()
        createVisiblNavButtonObserver()
        createTopPanel()
        initTopPanel()
    }

    private fun createTopPanel() {

    }

    private fun createInstrumentChangeObserver() {
        mainFragmentViewModel.currentInstrumentKeyParamsList.observe(this, Observer {
            if (mainFragmentViewModel.isCurrentInstrumentLoaded) {
                loadSamples(it)
                loadRandomGenerator(it)
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


    private fun loadSamples(instrumentKeyParamsList: ArrayList<InstrumentKeyParams>) {
        for (params in instrumentKeyParamsList) {
            loopPlayer.setInstrumentParamsKey(params)
        }

        stop_all.setOnClickListener { loopPlayer.stopAllSounds() }
        image_fon.setOnClickListener { fonHolder.setFonFor(fon_image) }
    }

    private fun loadRandomGenerator(instrumentKeyParamsList: ArrayList<InstrumentKeyParams>) {
        randomGenerator = RandomGenerator(
            loopPlayer,
            instrumentKeyParamsList
        )
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

        random_constr.setOnClickListener {
            isRandomOn = !isRandomOn
            randomGenerator?.press(isRandomOn)
            random_img.setPress(isRandomOn)
            random_text.setPress(isRandomOn)
        }
        random_img.setPress(isRandomOn)
        random_text.setPress(isRandomOn)

        fon_musick_constr.setOnClickListener { clickFinMusic() }

        fon_musick_right_button.setOnClickListener {
            if (isFonOn) {
                fonPlayer.nextTrack()
                fon_musick_trak_text.setText(fonPlayer.getTrackName())
            }
        }
        fon_musick_left_button.setOnClickListener {
            if (isFonOn) {
                fonPlayer.previusTrack()
                fon_musick_trak_text.setText(fonPlayer.getTrackName())
            }
        }
        fon_musick_on_img.setPress(isFonOn)
        fon_musick_on_text.setPress(isFonOn)
        fon_musick_trak_text.setDisabled(!isFonOn)
        fon_musick_title_text.setDisabled(!isFonOn)
        fon_musick_right_button.setDisabled(!isFonOn)
        fon_musick_left_button.setDisabled(!isFonOn)
    }

    private fun clickFinMusic() {
        if (sharedPref.loadBoolean(SoundFons.FON_LIST.sounds.get(0).instrumentName, false)) {
            isFonOn = fonPlayer.pressFon()
            fon_musick_on_img.setPress(isFonOn)
            fon_musick_on_text.setPress(isFonOn)
            fon_musick_trak_text.setDisabled(!isFonOn)
            fon_musick_title_text.setDisabled(!isFonOn)
            fon_musick_right_button.setDisabled(!isFonOn)
            fon_musick_left_button.setDisabled(!isFonOn)
            fon_musick_trak_text.setText(fonPlayer.getTrackName())
        } else {
            fon_musick_trak_text.setText(R.string.fon_music_loading)
        }
    }


    private fun getTopPanelButtonImage(isTop: Boolean): Int {
        return if (isTop) {
            R.drawable.ic_top_panel_to_down
        } else {
            R.drawable.ic_top_panel_to_top
        }
    }

    override fun onPause() {
        super.onPause()
        fonPlayer.stopMusic()
        isFonOn = false

        isRandomOn = false
        randomGenerator?.press(isRandomOn)
    }
}