package ru.lagarnikov.hapidrum.ui.main_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class MainFragmentViewModel : ViewModel() {

    var isDay = true
    val currentInstrumentKeyParamsList = MutableLiveData<ArrayList<InstrumentKeyParams>>()
    val visibilityNavigButton = MutableLiveData<Boolean>()
    var isCurrentInstrumentLoaded = false
    var isStopSound = MutableLiveData<Boolean>()

    init {
        isStopSound.value = false
    }
}