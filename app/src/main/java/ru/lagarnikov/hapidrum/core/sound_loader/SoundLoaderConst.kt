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

enum class Instruments(val sounds: ArrayList<InstrumentSound>) {
    MAIN(MAIN_SOUNDS_)
}