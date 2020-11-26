package ru.lagarnikov.hapidrum.ui.main_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class MainFragmentViewModel : ViewModel() {
    private val mainFragmentUseCase: IMainFragmentUseCase by KoinJavaComponent.inject(
        IMainFragmentUseCase::class.java
    )

    var isDay = true
    val currentInstrumentKeyParamsList = MutableLiveData<ArrayList<InstrumentKeyParams>>()
    val visibilityNavigButton = MutableLiveData<Boolean>()
    var isCurrentInstrumentLoaded = false
    var isStopSound = MutableLiveData<Boolean>()

    init {
        isStopSound.value = false
    }

    fun pressLeftNavButton() = mainFragmentUseCase.pressLeftNavButton()
    fun pressRightNavButton() = mainFragmentUseCase.pressRightNavButton()
    fun getStartInstrument() = mainFragmentUseCase.getStartInstrument()
}