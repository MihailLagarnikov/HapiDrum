package ru.lagarnikov.hapidrum.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.twosmalpixels.travel_notes.core.extension.setVisibility
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.core.sound_loader.SOUND_DIR
import java.io.File

abstract class BaseInstrumentFragment : Fragment(), ChildInstrumentFragmentListener {

    protected lateinit var progressViewModel: ProgressViewModel
    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(
        ISoundLoaderUseCase::class.java
    )
    private var listenerLoadFinish: ((ArrayList<InstrumentKeyParams>) -> Unit)? = null

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
    }

    private fun createProgressObserver() {
        val progressBar = view?.findViewById<ProgressBar>(R.id.progress)
        val content = view?.findViewById<ConstraintLayout>(R.id.content_instrument)
        progressViewModel =
            ViewModelProviders.of(requireActivity()).get(ProgressViewModel::class.java)
        progressViewModel.showProgress.observe(this, Observer { isShow ->
            progressBar?.setVisibility(isShow)
            content?.setVisibility(!isShow)
        })
    }

    private fun createLoadSoundObserver(){
        val isLoaded = soundLoaderUseCase.isInstrumentSoundLoaded(getInstrumentName())
        progressViewModel.showProgress.value = !isLoaded
        if (!isLoaded) {
            soundLoaderUseCase.isLoaded.observe(this, Observer {
                if (it && soundLoaderUseCase.getLoadingInstrument().equals(getInstrumentName())) {
                    progressViewModel.showProgress.value = false
                    listenerLoadFinish?.invoke(getInstrumentParams())
                }
            })
        }
    }

    override fun isLoaded() = soundLoaderUseCase.isInstrumentSoundLoaded(getInstrumentName())

    override fun awaitLoadFinish(listenerLoadFinish: (ArrayList<InstrumentKeyParams>) -> Unit) {
        this.listenerLoadFinish = listenerLoadFinish
    }

    protected fun getFilePath(): String{
        val pathFile = File(requireActivity().filesDir, SOUND_DIR)
        return pathFile.absolutePath
    }
}