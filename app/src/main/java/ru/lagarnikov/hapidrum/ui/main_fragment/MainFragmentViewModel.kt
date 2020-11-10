package ru.lagarnikov.hapidrum.ui.main_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {
    val instrumentChanged = MutableLiveData<Unit>()
}