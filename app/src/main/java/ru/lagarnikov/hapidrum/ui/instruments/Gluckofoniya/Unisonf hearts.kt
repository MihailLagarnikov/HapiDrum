package ru.lagarnikov.hapidrum.ui.instruments.Gluckofoniya

import android.os.Bundle
import android.view.View
import com.twosmalpixels.travel_notes.core.extension.setPressDrawable
import kotlinx.android.synthetic.main.unison_of_hearts_instrument_fragment.*
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.base_fragment.BaseInstrumentFragment
import ru.lagarnikov.hapidrum.core.sound_loader.UNISON_OF_HEARTS_
import ru.lagarnikov.hapidrum.core.sound_loader.UNISON_OF_HEARTS_SOUNDS_
import ru.lagarnikov.hapidrum.core.sound_loader.UNISON_OF_HEARTS_SOUNDS_FELT_
import ru.lagarnikov.hapidrum.core.sound_player.KeyValues
import ru.lagarnikov.hapidrum.model.AditionalInstrumentInfo
import ru.lagarnikov.hapidrum.model.InstrumentAboutData
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams

class UnisonOfHearts : BaseInstrumentFragment() {

    private val IS_HANDLE_SOUND_TYPE_UNISON_KEY = "sound_type_unison_key"

    override fun getLayout() = R.layout.unison_of_hearts_instrument_fragment

    override fun getInstrumentName() = UNISON_OF_HEARTS_

    override fun getInstrumentNameForAnalytic() = R.string.unison_of_hearts_name

    override fun getInstrumentAboutData(): InstrumentAboutData {
        return InstrumentAboutData(
            getString(R.string.unison_of_hearts_name),
            getString(R.string.shop_gluckofoniya),
            getString(R.string.url_gluckofoniya),
            arrayListOf(
                AditionalInstrumentInfo(
                    getString(R.string.diameter_title),
                    getString(R.string.diameter_unison_of_hearts)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.weight_title),
                    getString(R.string.weight_unison_of_hearts)
                ),
                AditionalInstrumentInfo(
                    getString(R.string.sound_title),
                    getString(R.string.sound_unison_of_hearts)
                )
            ),
            false
        )
    }

    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        return if (sharedPref.loadBoolean(IS_HANDLE_SOUND_TYPE_UNISON_KEY, false)) {
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
                    UNISON_OF_HEARTS_SOUNDS_.get(0).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(0).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(1).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(1).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(2).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(2).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(3).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(3).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(4).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(4).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(5).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(5).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(6).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(6).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_.get(7).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_.get(7).fileExtension
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
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(0).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(0).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.B,
                button_b,
                hapi_drum_b,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(1).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(1).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.C,
                button_c,
                hapi_drum_c,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(2).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(2).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.D,
                button_d,
                hapi_drum_d,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(3).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(3).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.E,
                button_e,
                hapi_drum_e,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(4).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(4).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.F,
                button_f,
                hapi_drum_f,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(5).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(5).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.G,
                button_g,
                hapi_drum_g,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(6).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(6).fileExtension
                )
            ),
            InstrumentKeyParams(
                KeyValues.H,
                button_h,
                hapi_drum_h,
                getFilePath(
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(7).fileLocalName,
                    UNISON_OF_HEARTS_SOUNDS_FELT_.get(7).fileExtension
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonState()
        felt.setOnClickListener {
            sharedPref.saveBoolean(IS_HANDLE_SOUND_TYPE_UNISON_KEY, false)
            mainFragmentViewModel.currentInstrumentKeyParamsList.value = getFeltSound()
            setButtonState()
        }
        handle.setOnClickListener {
            sharedPref.saveBoolean(IS_HANDLE_SOUND_TYPE_UNISON_KEY, true)
            mainFragmentViewModel.currentInstrumentKeyParamsList.value = getHandleSound()
            setButtonState()
        }
    }

    private fun setButtonState() {
        val isHandlTypeSound = sharedPref.loadBoolean(IS_HANDLE_SOUND_TYPE_UNISON_KEY, false)
        felt.setPressDrawable(!isHandlTypeSound)
        handle.setPressDrawable(isHandlTypeSound)
    }
}