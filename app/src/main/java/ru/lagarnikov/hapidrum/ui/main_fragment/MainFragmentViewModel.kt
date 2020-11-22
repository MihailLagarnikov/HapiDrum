package ru.lagarnikov.hapidrum.ui.main_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class MainFragmentViewModel : ViewModel() {

    val isDay = true
    val currentInstrumentKeyParamsList = MutableLiveData<ArrayList<InstrumentKeyParams>>()
    val visibilityNavigButton = MutableLiveData<Boolean>()
    var isCurrentInstrumentLoaded = false
}