package ru.lagarnikov.hapidrum.ui.main_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.firebase.analytics.FirebaseAnalytics
import com.twosmalpixels.travel_notes.core.extension.setDayNight
import com.twosmalpixels.travel_notes.core.extension.setDisabled
import com.twosmalpixels.travel_notes.core.extension.setPress
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.IS_NIGHT_THEME
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.BuildConfig
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.RandomGenerator
import ru.lagarnikov.hapidrum.core.analytics.*
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import ru.lagarnikov.hapidrum.core.sound_player.LoopPlayer
import ru.lagarnikov.hapidrum.core.fon_holder.IFonHolder
import ru.lagarnikov.hapidrum.core.sound_loader.SoundFons
import ru.lagarnikov.hapidrum.core.sound_player.IMyMediaPlayer
import ru.lagarnikov.hapidrum.ui.FilePathLoad
import kotlin.math.roundToInt


class MainFragment : Fragment() {

    private val sharedPref: ISharedPrefHelper by KoinJavaComponent.inject(ISharedPrefHelper::class.java)
    private val fonHolder: IFonHolder by KoinJavaComponent.inject(IFonHolder::class.java)
    private lateinit var loopPlayer: LoopPlayer
    private lateinit var fonPlayer: IMyMediaPlayer
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private var randomGenerator: RandomGenerator? = null
    private var isTopPanelHide = true
    private var isRandomOn = false
    private var isFonOn = false
    private var isFonImageOn = false
    private var isNightTheme = false

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
        loopPlayer = LoopPlayer()
        loopPlayer.mainFragmentViewModel = mainFragmentViewModel
        fonPlayer =
            MyMediaPlayer(requireContext(), (requireActivity() as FilePathLoad).getFileForLoading())
        createInstrumentChangeObserver()
        createVisiblNavButtonObserver()
        isNightTheme = sharedPref.loadBoolean(IS_NIGHT_THEME, false)
        initTopPanel()
        navigateInstrument(mainFragmentViewModel.getStartInstrument())

        btn_right_instrument.setOnClickListener {
            randomGenerator?.press(false)
            random_img.setPress(false)
            random_text.setPress(false)
            Analytics.logEventPushWithParameter(
                CLICK_NAVIGATE,
                FirebaseAnalytics.Param.ITEM_ID,
                CLICK_RIGHT
            )
            navigateInstrument(mainFragmentViewModel.pressRightNavButton())
        }
        btn_left_instrument.setOnClickListener {
            randomGenerator?.press(false)
            random_img.setPress(false)
            random_text.setPress(false)
            Analytics.logEventPushWithParameter(
                CLICK_NAVIGATE,
                FirebaseAnalytics.Param.ITEM_ID,
                CLICK_LEFT
            )
            navigateInstrument(mainFragmentViewModel.pressLeftNavButton())
        }
        mainFragmentViewModel.isStopSound.observe(viewLifecycleOwner, Observer {
            loopPlayer.stopAllSounds()
        })
        mainFragmentViewModel.deepLinkNavigateFragment.observe(viewLifecycleOwner, Observer {
            navigateInstrument(it)
        })
    }

    fun navigateInstrument(fragmentId: Int) {
        val fragmentContainer = requireView().findViewById<View>(R.id.instrument_container)
        val navController = Navigation.findNavController(fragmentContainer)
        navController.navigate(fragmentId)
    }

    private fun createInstrumentChangeObserver() {
        mainFragmentViewModel.currentInstrumentKeyParamsList.observe(viewLifecycleOwner, Observer {
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
        mainFragmentViewModel.visibilityNavigButton.observe(viewLifecycleOwner, Observer {
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
                setTopPanelParams(!isTopPanelHide)
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
                if (!isTopPanelHide) setTopPanelParams(!isTopPanelHide)
                isTopPanelHide = p1 == R.id.start
                hide_show_top_panel_button.setImageResource(getTopPanelButtonImage(isTopPanelHide))
                hide_show_top_panel_button.rotation = getTopPanelRotation(isTopPanelHide)
                Analytics.logEventPushWithParameter(
                    CLICK_TOP_PANEL_OPEN,
                    FirebaseAnalytics.Param.ITEM_ID,
                    isTopPanelHide.toString()
                )
            }
        })

        random_constr.setOnClickListener {
            isRandomOn = !isRandomOn
            Analytics.logEventPushWithParameter(
                CLICK_RANDOM,
                FirebaseAnalytics.Param.ITEM_ID,
                isRandomOn.toString()
            )
            randomGenerator?.press(isRandomOn)
            random_img.setPress(isRandomOn)
            random_text.setPress(isRandomOn)
        }
        random_img.setPress(isRandomOn)
        random_text.setPress(isRandomOn)

        fon_musick_constr.setOnClickListener { clickFonMusic() }

        fon_musick_right_button.setOnClickListener {
            if (isFonOn) {
                fonPlayer.nextTrack()
                fon_musick_trak_text.setText(fonPlayer.getTrackName())
                Analytics.logEventPushWithParameter(
                    CLICK_MUSICK_FON,
                    FirebaseAnalytics.Param.ITEM_ID,
                    CLICK_RIGHT
                )
            }
        }
        fon_musick_left_button.setOnClickListener {
            if (isFonOn) {
                fonPlayer.previusTrack()
                fon_musick_trak_text.setText(fonPlayer.getTrackName())
                Analytics.logEventPushWithParameter(
                    CLICK_MUSICK_FON,
                    FirebaseAnalytics.Param.ITEM_ID,
                    CLICK_LEFT
                )
            }
        }
        setFonMusickViewsParam()

        fon_img_constr.setOnClickListener {
            isFonImageOn = fonHolder.pressFonImageSwitch(fon_image)
            setFonImageParam()
            Analytics.logEventPushWithParameter(
                CLICK_IMG_FON,
                FirebaseAnalytics.Param.ITEM_ID,
                CLICK_ON_OFF
            )
        }
        fon_img_right_button.setOnClickListener {
            if (isFonImageOn) {
                fonHolder.nextTrack(fon_image)
                fon_img_trak_text.setText(fonHolder.getFonName())
                Analytics.logEventPushWithParameter(
                    CLICK_IMG_FON,
                    FirebaseAnalytics.Param.ITEM_ID,
                    CLICK_RIGHT
                )
            }
        }
        fon_img_left_button.setOnClickListener {
            if (isFonImageOn) {
                fonHolder.previusTrack(fon_image)
                fon_img_trak_text.setText(fonHolder.getFonName())
                Analytics.logEventPushWithParameter(
                    CLICK_IMG_FON,
                    FirebaseAnalytics.Param.ITEM_ID,
                    CLICK_LEFT
                )
            }
        }
        setFonImageParam()

        day_night_constr.setOnClickListener {
            isNightTheme = !isNightTheme
            sharedPref.saveBoolean(IS_NIGHT_THEME, isNightTheme)
            setNightThemeParam()
            val analyticValue = if (isNightTheme) R.string.night else R.string.day
            Analytics.logEventPushWithParameter(
                CLICK_DAY_NIGHT,
                FirebaseAnalytics.Param.ITEM_ID,
                analyticValue,
                requireContext()
            )
        }
        setNightThemeParam()
    }

    private fun setTopPanelParams(isHide: Boolean) {
        val params: FrameLayout.LayoutParams =
            motion_layout_top_panel.layoutParams as FrameLayout.LayoutParams
        if (isHide) {
            params.height = resources.getDimension(R.dimen.top_panel_height_min).roundToInt()
        } else {
            params.height = resources.getDimension(R.dimen.top_panel_height_max).roundToInt()
        }
        motion_layout_top_panel.layoutParams = params
    }

    private fun clickFonMusic() {
        Analytics.logEventPushWithParameter(
            CLICK_MUSICK_FON,
            FirebaseAnalytics.Param.ITEM_ID,
            CLICK_ON_OFF
        )
        if (sharedPref.loadBoolean(
                BuildConfig.VERSION_NAME + SoundFons.FON_LIST.sounds.get(0).instrumentName,
                false
            )
        ) {
            isFonOn = fonPlayer.pressSwitch()
            setFonMusickViewsParam()
            fon_musick_trak_text.setText(fonPlayer.getTrackName())
        } else {
            fon_musick_trak_text.setText(R.string.fon_music_loading)
        }
    }

    private fun setFonMusickViewsParam() {
        fon_musick_on_img.setPress(isFonOn)
        fon_musick_on_text.setPress(isFonOn)
        fon_musick_trak_text.setDisabled(!isFonOn)
        fon_musick_title_text.setDisabled(!isFonOn)
        fon_musick_right_button.setDisabled(!isFonOn)
        fon_musick_left_button.setDisabled(!isFonOn)
    }

    private fun setFonImageParam() {
        fon_img_on_img.setPress(isFonImageOn)
        fon_img_on_text.setPress(isFonImageOn)
        fon_img_title_text.setDisabled(!isFonImageOn)
        fon_img_trak_text.setDisabled(!isFonImageOn)
        fon_img_left_button.setDisabled(!isFonImageOn)
        fon_img_right_button.setDisabled(!isFonImageOn)
        fon_img_trak_text.setText(fonHolder.getFonName())
    }

    private fun setNightThemeParam() {
        day_night_text.setDayNight(isNightTheme)
        setFonImageParam()
        mainFragmentViewModel.isDay = !isNightTheme
        if (isNightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getTopPanelButtonImage(isTop: Boolean): Int {
        return if (isTop) {
            R.drawable.button_circle_arrow
        } else {
            R.drawable.ic_top_panel_to_top
        }
    }

    private fun getTopPanelRotation(isTop: Boolean): Float {
        return if (isTop) {
            270F
        } else {
            0F
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