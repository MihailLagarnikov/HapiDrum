package ru.lagarnikov.hapidrum.core.sound_loader

import ru.lagarnikov.hapidrum.model.InstrumentSound

const val EXT_WAV = ".wav"

const val MAIN_ = "main_instrument"
const val A_ = "a.wav"
const val B_ = "b.wav"
const val C_ = "c.wav"
const val D_ = "d.wav"
const val E_ = "e.wav"
const val F_ = "f.wav"
const val G_ = "g.wav"
const val H_ = "h.wav"
const val ZERO_ = "zero.wav"

const val A_LOCAL = "a_MAIN_INSTRUMENT"
const val B_LOCAL = "b_MAIN_INSTRUMENT"
const val C_LOCAL = "c_MAIN_INSTRUMENT"
const val D_LOCAL = "d_MAIN_INSTRUMENT"
const val E_LOCAL = "e_MAIN_INSTRUMENT"
const val F_LOCAL = "f_MAIN_INSTRUMENT"
const val G_LOCAL = "g_MAIN_INSTRUMENT"
const val H_LOCAL = "h_MAIN_INSTRUMENT"
const val ZERO_LOCAL = "zero_MAIN_INSTRUMENT"

const val UNISON_OF_HEARTS_ = "unison_of_hearts_instrument"
const val UNISON_OF_HEARTS_A_ = "unison_of_hearts_a.wav"
const val UNISON_OF_HEARTS_B_ = "unison_of_hearts_b.wav"
const val UNISON_OF_HEARTS_C_ = "unison_of_hearts_c.wav"
const val UNISON_OF_HEARTS_D_ = "unison_of_hearts_d.wav"
const val UNISON_OF_HEARTS_E_ = "unison_of_hearts_e.wav"
const val UNISON_OF_HEARTS_F_ = "unison_of_hearts_f.wav"
const val UNISON_OF_HEARTS_G_ = "unison_of_hearts_g.wav"
const val UNISON_OF_HEARTS_H_ = "unison_of_hearts_h.wav"

const val UNISON_OF_HEARTS_A_LOCAL = "a_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_B_LOCAL = "b_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_C_LOCAL = "c_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_D_LOCAL = "d_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_E_LOCAL = "e_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_F_LOCAL = "f_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_G_LOCAL = "g_UNISON_OF_HEARTS_"
const val UNISON_OF_HEARTS_H_LOCAL = "h_UNISON_OF_HEARTS_"

val MAIN_SOUNDS_ = arrayListOf<InstrumentSound>(
    InstrumentSound(MAIN_, A_, A_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, B_, B_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, C_, C_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, D_, D_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, E_, E_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, F_, F_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, G_, G_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, H_, H_LOCAL, EXT_WAV),
    InstrumentSound(MAIN_, ZERO_, ZERO_LOCAL, EXT_WAV)
)

val UNISON_OF_HEARTS_SOUNDS_ = arrayListOf<InstrumentSound>(
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_A_, UNISON_OF_HEARTS_A_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_B_, UNISON_OF_HEARTS_B_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_C_, UNISON_OF_HEARTS_C_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_D_, UNISON_OF_HEARTS_D_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_E_, UNISON_OF_HEARTS_E_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_F_, UNISON_OF_HEARTS_F_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_G_, UNISON_OF_HEARTS_G_LOCAL, EXT_WAV),
    InstrumentSound(UNISON_OF_HEARTS_, UNISON_OF_HEARTS_H_, UNISON_OF_HEARTS_H_LOCAL, EXT_WAV)
)

enum class Instruments(val sounds: ArrayList<InstrumentSound>) {
    MAIN(MAIN_SOUNDS_),
    UNISON_OF_HEARTS(UNISON_OF_HEARTS_SOUNDS_)
}