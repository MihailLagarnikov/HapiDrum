package ru.lagarnikov.hapidrum.core

interface ChildInstrumentFragmentListener {
    fun getInstrumentParams(): ArrayList<InstrumentKeyParams>
    fun isLoaded(): Boolean
    fun awaitLoadFinish(listenerLoadFinish: (ArrayList<InstrumentKeyParams>) -> Unit )
}