package ru.lagarnikov.hapidrum.ui.instruments.main_instrument

import kotlinx.android.synthetic.main.main_instrument_fragment.*
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.BaseInstrumentFragment
import ru.lagarnikov.hapidrum.core.InstrumentKeyParams
import ru.lagarnikov.hapidrum.core.sound_loader.MAIN_
import ru.lagarnikov.hapidrum.core.sound_loader.MAIN_SOUNDS_
import ru.lagarnikov.hapidrum.soundlayer.KeyValues

class MainInstrument : BaseInstrumentFragment() {

    override fun getLayout() = R.layout.main_instrument_fragment

    override fun getInstrumentName() = MAIN_

    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        return arrayListOf(
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                "${getFilePath()}/${MAIN_SOUNDS_.get(0).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                "${getFilePath()}/${MAIN_SOUNDS_.get(1).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                "${getFilePath()}/${MAIN_SOUNDS_.get(2).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                "${getFilePath()}/${MAIN_SOUNDS_.get(3).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                "${getFilePath()}/${MAIN_SOUNDS_.get(4).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                "${getFilePath()}/${MAIN_SOUNDS_.get(5).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                "${getFilePath()}/${MAIN_SOUNDS_.get(6).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                "${getFilePath()}/${MAIN_SOUNDS_.get(7).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                "${getFilePath()}/${MAIN_SOUNDS_.get(8).soundName}"
            ),
            InstrumentKeyParams(
                KeyValues.ZERO,
                button_zero,
                hapi_drum_zero,
                "${getFilePath()}/${MAIN_SOUNDS_.get(9).soundName}"
            )
        )
    }
}