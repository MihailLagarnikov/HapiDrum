package ru.lagarnikov.hapidrum.ui.instruments.Gluckofoniya

import android.os.Bundle
import android.view.View
import com.twosmalpixels.travel_notes.core.extension.setPressDrawable
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_a
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_b
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_c
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_d
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_e
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_f
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_g
import kotlinx.android.synthetic.main.open_space_instrument_fragment.button_h
import kotlinx.android.synthetic.main.open_space_instrument_fragment.felt
import kotlinx.android.synthetic.main.open_space_instrument_fragment.handle
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_a
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_b
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_c
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_d
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_e
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_f
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_g
import kotlinx.android.synthetic.main.open_space_instrument_fragment.hapi_drum_h
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.base_fragment.BaseInstrumentFragment
import ru.lagarnikov.hapidrum.core.sound_loader.*
import ru.lagarnikov.hapidrum.core.sound_player.KeyValues
import ru.lagarnikov.hapidrum.model.AditionalInstrumentInfo
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class OpenSpace: BaseInstrumentFragment() {

    private val IS_HANDLE_SOUND_TYPE_OPEN_SPASE_KEY = "sound_type_open_space_key"

    override fun getLayout() = R.layout.open_space_instrument_fragment

    override fun getInstrumentName() = OPEN_SPACE_

    override fun getInstrumentNameForAnalytic() = R.string.open_space_name

    override fun getInstrumentAboutData(): InstrumentAboutData {
        return InstrumentAboutData(
            getString(R.string.open_space_name),
            getString(R.string.shop_gluckofoniya),
            getString(R.string.url_gluckofoniya),
            arrayListOf(
                AditionalInstrumentInfo(
                    getString(R.string.diameter_title),
                    getString(R.string.diameter_open_space)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.weight_title),
                    getString(R.string.weight_open_space)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.sound_title),
                    getString(R.string.sound_open_space)
                )
            ),
            false
        )
    }

    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        return if (sharedPref.loadBoolean(IS_HANDLE_SOUND_TYPE_OPEN_SPASE_KEY, false)) {
            getHandleSound()
        } else {
            getFeltSound()
        }

    }

    private fun getHandleSound(): ArrayList<InstrumentKeyParams> {
        return arrayListOf(
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(0).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(0).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(1).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(1).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(2).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(2).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(3).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(3).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(4).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(4).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(5).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(5).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(6).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(6).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(
                    OPEN_SPACE_SOUNDS_.get(7).fileLocalName,
                    OPEN_SPACE_SOUNDS_.get(7).fileExtension
                )
            )
        )
    }

    private fun getFeltSound(): ArrayList<InstrumentKeyParams> {
        return arrayListOf(
            InstrumentKeyParams(
                KeyValues.A,
                button_a,
                hapi_drum_a,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(0).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(0).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(1).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(1).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(2).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(2).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(3).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(3).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(4).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(4).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(5).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(5).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(6).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(6).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(
                    OPEN_SPACE_SOUNDS_FELT_.get(7).fileLocalName,
                    OPEN_SPACE_SOUNDS_FELT_.get(7).fileExtension
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonState()
        felt.setOnClickListener {
            sharedPref.saveBoolean(IS_HANDLE_SOUND_TYPE_OPEN_SPASE_KEY, false)
            mainFragmentViewModel.currentInstrumentKeyParamsList.value = getFeltSound()
            setButtonState()
        }
        handle.setOnClickListener {
            sharedPref.saveBoolean(IS_HANDLE_SOUND_TYPE_OPEN_SPASE_KEY, true)
            mainFragmentViewModel.currentInstrumentKeyParamsList.value = getHandleSound()
            setButtonState()
        }
    }

    private fun setButtonState() {
        val isHandlTypeSound = sharedPref.loadBoolean(IS_HANDLE_SOUND_TYPE_OPEN_SPASE_KEY, false)
        felt.setPressDrawable(!isHandlTypeSound)
        handle.setPressDrawable(isHandlTypeSound)
    }
}