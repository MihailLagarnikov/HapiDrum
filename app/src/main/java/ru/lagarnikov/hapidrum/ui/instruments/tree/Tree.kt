package ru.lagarnikov.hapidrum.ui.instruments.tree


import kotlinx.android.synthetic.main.tree_fragment.*
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.base_fragment.BaseInstrumentFragment
import ru.lagarnikov.hapidrum.core.sound_loader.MAIN_
import ru.lagarnikov.hapidrum.core.sound_loader.MAIN_SOUNDS_
import ru.lagarnikov.hapidrum.core.sound_player.KeyValues
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class Tree : BaseInstrumentFragment() {

    override fun getLayout() = R.layout.tree_fragment

    override fun getInstrumentName() = MAIN_

    override fun getInstrumentAboutData(): InstrumentAboutData {
        return return InstrumentAboutData(
            getString(R.string.instrument_main),
            getString(R.string.shop_kosmosky),
            getString(R.string.url_kosmosky),
            ArrayList(),
            false,
            getString(R.string.instrument_tree)
        )
    }


    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        return arrayListOf(
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                getFilePath(MAIN_SOUNDS_.get(0).fileLocalName, MAIN_SOUNDS_.get(0).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(MAIN_SOUNDS_.get(1).fileLocalName, MAIN_SOUNDS_.get(1).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(MAIN_SOUNDS_.get(2).fileLocalName, MAIN_SOUNDS_.get(2).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(MAIN_SOUNDS_.get(3).fileLocalName, MAIN_SOUNDS_.get(3).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(MAIN_SOUNDS_.get(4).fileLocalName, MAIN_SOUNDS_.get(4).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(MAIN_SOUNDS_.get(5).fileLocalName, MAIN_SOUNDS_.get(5).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(MAIN_SOUNDS_.get(6).fileLocalName, MAIN_SOUNDS_.get(6).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(MAIN_SOUNDS_.get(7).fileLocalName, MAIN_SOUNDS_.get(7).fileExtension)
            ),
            InstrumentKeyParams(
                KeyValues.ZERO,
                button_zero,
                hapi_drum_zero,
                getFilePath(MAIN_SOUNDS_.get(8).fileLocalName, MAIN_SOUNDS_.get(8).fileExtension)
            )
        )
    }
}