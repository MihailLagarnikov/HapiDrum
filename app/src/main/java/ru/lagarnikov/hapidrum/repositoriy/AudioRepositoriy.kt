package ru.lagarnikov.hapidrum.repositoriy

import ru.lagarnikov.hapidrum.R

class AudioRepositoriy {
    val listMusic = ArrayList<Int>()
    init {
        listMusic.add(R.raw.fon_a)
        listMusic.add(R.raw.fon_b)
        listMusic.add(R.raw.fon_c)
        listMusic.add(R.raw.fon_d)
        listMusic.add(R.raw.fon_e)
    }
}