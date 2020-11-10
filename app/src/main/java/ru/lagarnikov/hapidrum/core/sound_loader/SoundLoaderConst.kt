package ru.lagarnikov.hapidrum.core.sound_loader

import ru.lagarnikov.hapidrum.core.InstrumentSound

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


val MAIN_SOUNDS_ = arrayListOf<InstrumentSound>(
    InstrumentSound(MAIN_, A_, A_),
    InstrumentSound(MAIN_, B_, B_),
    InstrumentSound(MAIN_, C_, C_),
    InstrumentSound(MAIN_, D_, D_),
    InstrumentSound(MAIN_, E_, E_),
    InstrumentSound(MAIN_, F_, F_),
    InstrumentSound(MAIN_, G_, G_),
    InstrumentSound(MAIN_, H_, H_),
    InstrumentSound(MAIN_, ZERO_, ZERO_)
)


enum class Instruments(val sounds: ArrayList<InstrumentSound>) {
    MAIN(MAIN_SOUNDS_)
}