package ru.lagarnikov.hapidrum.core.fon_repositoriy

import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.model.FonImage

enum class Fons(val param: FonImage) {
    FON_A(FonImage(R.drawable.fon_a, R.string.fon_a_day, R.string.fon_a_night))
}