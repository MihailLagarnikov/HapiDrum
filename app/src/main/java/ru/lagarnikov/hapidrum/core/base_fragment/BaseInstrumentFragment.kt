package ru.lagarnikov.hapidrum.core.base_fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.twosmalpixels.travel_notes.core.extension.setVisibility
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.ui.main_fragment.MainFragmentViewModel
import java.io.File

abstract class BaseInstrumentFragment : Fragment(),
    ChildInstrumentFragmentListener, View.OnClickListener {

    private val SCALE_FORWARD = 0.8f
    private val SCALE_BACK = 1f

    protected lateinit var progressViewModel: ProgressViewModel
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(
        ISoundLoaderUseCase::class.java
    )
    private var motionLayout: MotionLayout? = null
    private var imgClose: ImageView? = null
    private var imageInstrument: ImageView? = null
    private var textClose: TextView? = null

    abstract fun getLayout(): Int
    abstract fun getInstrumentName(): String
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
        progressViewModel.showProgress.observe(this, Observer { isShow ->
            progressBar?.setVisibility(isShow)
            content?.setVisibility(!isShow)
        })
    }

    private fun createLoadSoundObserver() {
        val isLoaded = soundLoaderUseCase.isInstrumentSoundLoaded(getInstrumentName())
        mainFragmentViewModel =
            ViewModelProviders.of(requireActivity()).get(MainFragmentViewModel::class.java)
        mainFragmentViewModel.isCurrentInstrumentLoaded = isLoaded
        progressViewModel.showProgress.value = !isLoaded
        if (!isLoaded) {
            soundLoaderUseCase.isLoaded.observe(this, Observer {
                if (it && soundLoaderUseCase.getLoadingInstrumentName().equals(getInstrumentName())) {
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
        openShop?.setOnClickListener {
            motionLayout?.transitionToEnd()
            scaleInstrumentImg(imageInstrument, SCALE_FORWARD)
            Handler().postDelayed({
                imgClose?.setVisibility(View.VISIBLE)
                textClose?.setVisibility(View.VISIBLE)
            }, resources.getInteger(R.integer.anim_duration_medium).toLong())
            mainFragmentViewModel.visibilityNavigButton.value = false
        }
        imgClose?.setOnClickListener(this)
        textClose?.setOnClickListener(this)
    }

    private fun setInstrumentsAbout() {
        val info = getInstrumentAboutData()
        val instrName = view?.findViewById<TextView>(R.id.instrument_name)
        val shopName = view?.findViewById<TextView>(R.id.shop_name)
        val shopNameLink = view?.findViewById<TextView>(R.id.link_shop_name)
        instrName?.text = info.name
        shopName?.text = info.shopName
        shopNameLink?.text = info.shopName
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

    override fun isLoaded() = soundLoaderUseCase.isInstrumentSoundLoaded(getInstrumentName())

    protected fun getFilePath(name: String, ext: String): String {
        val sdDir: File = Environment.getExternalStorageDirectory()
        return File(sdDir, name + ext).absolutePath
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.text_close || p0?.id == R.id.image_close) {
            imgClose?.setVisibility(View.GONE)
            textClose?.setVisibility(View.GONE)
            motionLayout?.transitionToStart()
            scaleInstrumentImg(imageInstrument, SCALE_BACK)
            Handler().postDelayed(
                { mainFragmentViewModel.visibilityNavigButton.value = true },
                resources.getInteger(R.integer.anim_duration_medium).toLong()
            )
        }
    }
}