package ru.lagarnikov.hapidrum.core.fon_holder

import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.model.FonImage

enum class Fons(val param: FonImage) {
    FON_A(
        FonImage(
            R.drawable.marble,
            R.drawable.sky_blue,
            R.string.fon_a_day,
            R.string.fon_a_night
        )
    ),
    FON_B(
        FonImage(
            R.drawable.watercolor,
            R.drawable.abstract_night,
            R.string.fon_b_day,
            R.string.fon_b_night
        )
    )
}