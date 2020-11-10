package ru.lagarnikov.hapidrum.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseInstrumentFragment : Fragment() {

    abstract fun getLayout(): Int
    abstract fun getInstrumentSoundList(): ArrayList<InstrumentSound>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }
}