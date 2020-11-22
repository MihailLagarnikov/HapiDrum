package ru.lagarnikov.hapidrum.core.sound_loader

import ru.lagarnikov.hapidrum.model.InstrumentSound

const val FON_ = "fon_sound"
const val EXT_MP3 = ".mp3"

const val FON_A_ = "fon_a.mp3"
const val FON_B_ = "fon_b.mp3"
const val FON_C_ = "fon_c.mp3"
const val FON_D_ = "fon_d.mp3"
const val FON_E_ = "fon_e.mp3"

const val FON_A_LOCAL = "fon_a_local"
const val FON_B_LOCAL = "fon_b_local"
const val FON_C_LOCAL = "fon_c_local"
const val FON_D_LOCAL = "fon_d_local"
const val FON_E_LOCAL = "fon_e_local"

val FON_LIST_ = arrayListOf<InstrumentSound>(
    InstrumentSound(FON_, FON_A_, FON_A_LOCAL, EXT_MP3),
    InstrumentSound(FON_, FON_B_, FON_B_LOCAL, EXT_MP3),
    InstrumentSound(FON_, FON_C_, FON_C_LOCAL, EXT_MP3),
    InstrumentSound(FON_, FON_D_, FON_D_LOCAL, EXT_MP3),
    InstrumentSound(FON_, FON_E_, FON_E_LOCAL, EXT_MP3)
)

enum class SoundFons(val sounds: ArrayList<InstrumentSound>) {
    FON_LIST(FON_LIST_)
}