package ru.lagarnikov.hapidrum.core.base_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgressViewModel : ViewModel() {

    val showProgress = MutableLiveData<Boolean>()
    init {
        showProgress.value = false
    }
}