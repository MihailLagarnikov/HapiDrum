package ru.lagarnikov.hapidrum.core.base_fragment

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.twosmalpixels.travel_notes.core.extension.setVisibility
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.ui.main_fragment.MainFragmentViewModel
import java.io.File

abstract class BaseInstrumentFragment : Fragment(),
    ChildInstrumentFragmentListener {

    protected lateinit var progressViewModel: ProgressViewModel
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(
        ISoundLoaderUseCase::class.java
    )

    abstract fun getLayout(): Int
    abstract fun getInstrumentName(): String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createProgressObserver()
        createLoadSoundObserver()
        createShopOpenBlock()
    }

    private fun createProgressObserver() {
        val progressBar = view?.findViewById<ConstraintLayout>(R.id.progress)
        val content = view?.findViewById<ConstraintLayout>(R.id.content_instrument)
        progressViewModel =
            ViewModelProviders.of(requireActivity()).get(ProgressViewModel::class.java)
        mainFragmentViewModel =
            ViewModelProviders.of(requireActivity()).get(MainFragmentViewModel::class.java)
        progressViewModel.showProgress.observe(this, Observer { isShow ->
            progressBar?.setVisibility(isShow)
            content?.setVisibility(!isShow)
        })
    }

    private fun createLoadSoundObserver() {
        val isLoaded = soundLoaderUseCase.isInstrumentSoundLoaded(getInstrumentName())
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

    private fun createShopOpenBlock(){
        val motionLayout = view?.findViewById<MotionLayout>(R.id.motion_instrument)
        val openShop = view?.findViewById<TextureView>(R.id.link_shop_name)
        openShop?.setOnClickListener { motionLayout?.transitionToEnd() }
    }

    override fun isLoaded() = soundLoaderUseCase.isInstrumentSoundLoaded(getInstrumentName())

    protected fun getFilePath(name: String, ext: String): String {
        val sdDir: File = Environment.getExternalStorageDirectory()
        return File(sdDir, name + ext).absolutePath
    }
}