package ru.lagarnikov.hapidrum.ui.instruments.steklov


import kotlinx.android.synthetic.main.night_flower_instrument_fragment.*
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.base_fragment.BaseInstrumentFragment
import ru.lagarnikov.hapidrum.core.sound_loader.*
import ru.lagarnikov.hapidrum.core.sound_player.KeyValues
import ru.lagarnikov.hapidrum.model.AditionalInstrumentInfo
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class NightFlower : BaseInstrumentFragment(), BaseInstrumentFragment.TenKeyInstrument {


    override fun getLayout() = R.layout.night_flower_instrument_fragment

    override fun getInstrumentName() = NIGHT_FLOWER_

    override fun getInstrumentNameForAnalytic() = R.string.night_flower_name

    override fun getInstrumentAboutData(): InstrumentAboutData {
        return InstrumentAboutData(
            getString(R.string.night_flower_name),
            getString(R.string.shop_steklov),
            getString(R.string.url_steklov),
            arrayListOf(
                AditionalInstrumentInfo(
                    getString(R.string.diameter_title),
                    getString(R.string.diameter_night_flower)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.weight_title),
                    getString(R.string.weight_night_flower)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.sound_title),
                    getString(R.string.sound_night_flower)
                )
            ),
            false
        )
    }

    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        return return arrayListOf(
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(0).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(0).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(1).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(1).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(2).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(2).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(3).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(3).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(4).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(4).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(5).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(5).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(6).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(6).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(7).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(7).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.I,
                button_i,
                hapi_drum_i,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(8).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(8).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.J,
                button_j,
                hapi_drum_j,
                getFilePath(
                    NIGHT_FLOWER_SOUNDS_.get(9).fileLocalName,
                    NIGHT_FLOWER_SOUNDS_.get(9).fileExtension
                )
            )
        )

    }
}