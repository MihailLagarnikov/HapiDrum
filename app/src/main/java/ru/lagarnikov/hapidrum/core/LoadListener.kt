package ru.lagarnikov.hapidrum.core

interface LoadListener {
    fun setLoadState(state: StateLoad, instrumentSound: InstrumentSound)
}

enum class StateLoad {
    LOAD,
    SUCCESS,
    FAILURE
}