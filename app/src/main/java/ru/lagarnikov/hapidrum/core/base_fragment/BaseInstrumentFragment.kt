package ru.lagarnikov.hapidrum.core.base_fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.analytics.FirebaseAnalytics
import com.twosmalpixels.travel_notes.core.extension.setVisibility
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.BuildConfig
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.analytics.*
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.ui.FilePathLoad
import ru.lagarnikov.hapidrum.ui.main_fragment.MainFragmentViewModel
import java.io.File

abstract class BaseInstrumentFragment : Fragment(),
    ChildInstrumentFragmentListener, View.OnClickListener {

    val MARKET_PREFIX = "market://details?id="
    val RESULT_AFTER_RATE = 368
    private val SCALE_FORWARD = 0.8f
    private val SCALE_BACK = 1f

    protected lateinit var progressViewModel: ProgressViewModel
    protected lateinit var mainFragmentViewModel: MainFragmentViewModel
    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(
        ISoundLoaderUseCase::class.java
    )
    protected val sharedPref: ISharedPrefHelper by KoinJavaComponent.inject(ISharedPrefHelper::class.java)
    private var motionLayout: MotionLayout? = null
    private var imgClose: ImageView? = null
    private var imageInstrument: ImageView? = null
    private var textClose: TextView? = null

    abstract fun getLayout(): Int
    abstract fun getInstrumentName(): String
    abstract fun getInstrumentNameForAnalytic(): Int
    abstract fun getInstrumentAboutData(): InstrumentAboutData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        createProgressObserver()
        createLoadSoundObserver()
        createShopOpenBlock()
        setInstrumentsAbout()
        createStopAllSoundsObserver()
        val stopAll = view.findViewById<ImageView>(R.id.stop_all)
        stopAll?.run {
            this.setOnClickListener {
                mainFragmentViewModel.isStopSound.value = mainFragmentViewModel.isStopSound.value
            }
        }
        Analytics.logEventPushWithParameter(
            OPEN_INSTRUMENT,
            FirebaseAnalytics.Param.ITEM_ID,
            getInstrumentNameForAnalytic(),
            requireContext()
        )
    }

    private fun findViews() {
        motionLayout = view?.findViewById(R.id.motion_instrument)
        imgClose = view?.findViewById(R.id.image_close)
        imageInstrument = view?.findViewById(R.id.hapi_drum)
        textClose = view?.findViewById(R.id.text_close)
    }

    private fun createProgressObserver() {
        val progressBar = view?.findViewById<ConstraintLayout>(R.id.progress)
        val content = view?.findViewById<ConstraintLayout>(R.id.content_instrument)
        progressViewModel =
            ViewModelProviders.of(requireActivity()).get(ProgressViewModel::class.java)
        progressViewModel.showProgress.observe(viewLifecycleOwner, Observer { isShow ->
            progressBar?.setVisibility(false)
            content?.setVisibility(!isShow)
        })
    }

    private fun createLoadSoundObserver() {
        val isLoaded = sharedPref.loadBoolean(BuildConfig.VERSION_NAME + getInstrumentName(), false)
        mainFragmentViewModel =
            ViewModelProviders.of(requireActivity()).get(MainFragmentViewModel::class.java)
        mainFragmentViewModel.isCurrentInstrumentLoaded = isLoaded
        progressViewModel.showProgress.value = !isLoaded
        if (!isLoaded) {
            soundLoaderUseCase.isLoaded.observe(viewLifecycleOwner, Observer {
                if (it) {
                    progressViewModel.showProgress.value = false
                    mainFragmentViewModel.isCurrentInstrumentLoaded = true
                    mainFragmentViewModel.currentInstrumentKeyParamsList.value =
                        getInstrumentParams()
                }
            })
        } else {
            mainFragmentViewModel.currentInstrumentKeyParamsList.value = getInstrumentParams()
        }
    }

    private fun createShopOpenBlock() {
        val openShop = view?.findViewById<TextView>(R.id.link_shop_name)
        openShop?.isEnabled = !getInstrumentAboutData().withoutLink
        openShop?.setOnClickListener {
            motionLayout?.transitionToEnd()
            scaleInstrumentImg(imageInstrument, SCALE_FORWARD)
            Handler().postDelayed({
                imgClose?.setVisibility(View.VISIBLE)
                textClose?.setVisibility(View.VISIBLE)
            }, resources.getInteger(R.integer.anim_duration_medium).toLong())
            mainFragmentViewModel.visibilityNavigButton.value = false
            mainFragmentViewModel.isStopSound.value = true
            Analytics.logEventPushWithParameter(
                CLICK_BUTTON_SHOW_INFO,
                FirebaseAnalytics.Param.ITEM_ID,
                CLICK_OPEN
            )
        }
        imgClose?.setOnClickListener(this)
        textClose?.setOnClickListener(this)
    }

    private fun setInstrumentsAbout() {
        val info = getInstrumentAboutData()
        val instrName = view?.findViewById<TextView>(R.id.instrument_name)
        val shopName = view?.findViewById<TextView>(R.id.shop_name)
        val shopNameLink = view?.findViewById<TextView>(R.id.link_shop_name)
        instrName?.text = if (info.fakeName.isEmpty()) info.name else info.fakeName
        shopName?.text = info.shopName
        shopNameLink?.text = if (info.withoutLink) null else info.shopName
        if (info.withoutLink) {
            view?.findViewById<ImageView>(R.id.link_line)?.setColorFilter(
                requireActivity().resources.getColor(R.color.white),
                PorterDuff.Mode.SRC_IN
            )
        }
        val listAdditional = info.aditinalParams
        if (!listAdditional.isEmpty()) {
            var i = 0
            for (param in listAdditional) {
                when (i) {
                    0 -> {
                        view?.findViewById<TextView>(R.id.title_diameter)?.text = param.parametr
                        view?.findViewById<TextView>(R.id.shop_diameter)?.text = param.value
                    }
                    1 -> {
                        view?.findViewById<TextView>(R.id.title_weight)?.text = param.parametr
                        view?.findViewById<TextView>(R.id.shop_weight)?.text = param.value
                    }
                    2 -> {
                        view?.findViewById<TextView>(R.id.title_sound)?.text = param.parametr
                        view?.findViewById<TextView>(R.id.shop_sound)?.text = param.value
                    }
                }
                i++
            }
        }
        if (info.shopNameTitle.isNotEmpty()) {
            view?.findViewById<TextView>(R.id.title_shop_name)?.setText(info.shopNameTitle)
        }
        if (info.diametrTitle.isNotEmpty()) {
            view?.findViewById<TextView>(R.id.title_diameter)?.setText(info.diametrTitle)
        }
        if (info.weightTitle.isNotEmpty()) {
            view?.findViewById<TextView>(R.id.title_weight)?.setText(info.weightTitle)
        }
        if (listAdditional.size < 1) {
            view?.findViewById<TextView>(R.id.title_diameter)?.setText(null)
        }
        if (listAdditional.size < 2) {
            view?.findViewById<TextView>(R.id.title_weight)?.setText(null)
        }
        if (listAdditional.size < 3) {
            view?.findViewById<TextView>(R.id.title_sound)?.setText(null)
        }
        val buttonShop = view?.findViewById<Button>(R.id.button_shop)
        buttonShop?.setVisibility(info.urlShop.isNotEmpty())
        buttonShop?.setOnClickListener {
            Analytics.logEventPushWithParameter(
                CLICK_BUTTON_GO_STORE,
                FirebaseAnalytics.Param.ITEM_ID,
                info.urlShop
            )
            if (info.urlShop.equals(getString(R.string.url_2smallPixels))) {
                rateStore()
            } else {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(info.urlShop))
                startActivity(intent)
            }
        }
        if (info.urlShop.equals(getString(R.string.url_2smallPixels))) {
            buttonShop?.setText(R.string.rate_2smallPixels)
        }

    }

    private fun rateStore() {
        val uri: Uri = Uri.parse(MARKET_PREFIX + requireActivity().packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        startActivityForResult(goToMarket, RESULT_AFTER_RATE)
    }

    private fun scaleInstrumentImg(view: View?, scale: Float) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scale)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scale)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
        animator.repeatCount = 0
        animator.repeatMode = ObjectAnimator.RESTART
        animator.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()
        animator.start()
    }

    protected fun getFilePath(name: String, ext: String): String {
        val fileDir = (requireActivity() as FilePathLoad).getFileForLoading()
        return File(fileDir, name + ext).absolutePath
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.text_close || p0?.id == R.id.image_close) {
            Analytics.logEventPushWithParameter(
                CLICK_BUTTON_SHOW_INFO,
                FirebaseAnalytics.Param.ITEM_ID,
                CLICK_CLOSE
            )
            imgClose?.setVisibility(View.GONE)
            textClose?.setVisibility(View.GONE)
            motionLayout?.transitionToStart()
            scaleInstrumentImg(imageInstrument, SCALE_BACK)
            Handler().postDelayed(
                { mainFragmentViewModel.visibilityNavigButton.value = true },
                resources.getInteger(R.integer.anim_duration_medium).toLong()
            )
            mainFragmentViewModel.isStopSound.value = false
        }
    }

    private fun createStopAllSoundsObserver() {
        mainFragmentViewModel.isStopSound.observe(viewLifecycleOwner, Observer {
            for (instrKeyParam in getInstrumentParams()) {
                instrKeyParam.button.isEnabled = !it
            }
        })
    }

    interface TenKeyInstrument
}