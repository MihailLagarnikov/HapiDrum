package ru.lagarnikov.hapidrum.core

interface LoadListener {
    fun setLoadState(state: StateLoad, instrumentSound: InstrumentSound)
}

enum class StateLoad {
    LOAD,
    SUCCESS,
    FAILURE
}

fun getSimpleState(isSuccsses: Boolean): StateLoad {
    return if (isSuccsses) {
        StateLoad.SUCCESS
    } else {
        StateLoad.FAILURE
    }
}