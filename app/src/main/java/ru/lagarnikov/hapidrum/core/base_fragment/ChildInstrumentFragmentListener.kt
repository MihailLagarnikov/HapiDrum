package ru.lagarnikov.hapidrum.core.base_fragment

import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

interface ChildInstrumentFragmentListener {
    fun getInstrumentParams(): ArrayList<InstrumentKeyParams>
    fun isLoaded(): Boolean
}