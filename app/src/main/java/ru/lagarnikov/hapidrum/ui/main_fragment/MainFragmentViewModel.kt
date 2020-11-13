package ru.lagarnikov.hapidrum.ui.main_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class MainFragmentViewModel : ViewModel() {

    val currentInstrumentKeyParamsList = MutableLiveData<ArrayList<InstrumentKeyParams>>()
    var isCurrentInstrumentLoaded = false
}