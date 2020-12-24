package ru.lagarnikov.hapidrum.core.sound_player

import android.annotation.SuppressLint
import android.media.SoundPool
import android.view.MotionEvent
import android.view.View
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import ru.lagarnikov.hapidrum.core.AnimTouch
import ru.lagarnikov.hapidrum.model.StreamAndKeyParam
import ru.lagarnikov.hapidrum.ui.main_fragment.MainFragmentViewModel
import java.util.*
import kotlin.collections.ArrayList

class LoopPlayer {

    private val MAX_STREAM = 30
    private val PRIORITY = 1
    private val LEFT_VALUME_DEF = 1f
    private val RIGHT_VALUME_DEF = 1f
    private val RATE_DEF = 1f

    private val mTouchParam = TouchParam()
    private val mStreamListWithKeys = ArrayList<StreamAndKeyParam>()
    private val mViewList = ArrayList<View>()
    var mainFragmentViewModel: MainFragmentViewModel? = null

    private val mSoundPool = SoundPool.Builder()
        .setMaxStreams(MAX_STREAM)
        .build()

    @SuppressLint("ClickableViewAccessibility")
    fun setInstrumentParamsKey(instrumentKeyParams: InstrumentKeyParams) {
        mViewList.add(instrumentKeyParams.button)
        val anim = AnimTouch()
        val sound = mSoundPool.load(instrumentKeyParams.fileName, PRIORITY)
        mStreamListWithKeys.add(StreamAndKeyParam(sound, instrumentKeyParams))
        var stream: Int? = null

        var startTime = 0L
        instrumentKeyParams.button.setOnTouchListener { v, event ->
            val calendar = Calendar.getInstance()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTime = calendar.time.time
                    anim.startAnimSound(instrumentKeyParams.animView)
                    mainFragmentViewModel?.keyPressedId?.value = instrumentKeyParams.button.id
                    mainFragmentViewModel?.isKeyPressed?.value = true
                }
                MotionEvent.ACTION_UP -> {
                    val stopTime = calendar.time.time
                    stream = touchSoundEvent(
                        stopTime - startTime,
                        sound,
                        stream,
                        instrumentKeyParams.keyParams
                    )
                    anim.stopAnimSound()
                    mainFragmentViewModel?.isKeyPressed?.value = false
                }
            }
            true
        }
    }

    private fun touchSoundEvent(
        timeTouch: Long,
        soundId: Int?,
        streamId: Int?,
        sondName: KeyValues
    ): Int? {
        soundId?.run {
            if (streamId != null) {
                mSoundPool.stop(streamId)
            }
            mSoundPool.autoResume()
            return mSoundPool.play(
                soundId,
                LEFT_VALUME_DEF * mTouchParam.getTouchChangeValue(timeTouch) * sondName.leftValueParam,
                RIGHT_VALUME_DEF * mTouchParam.getTouchChangeValue(timeTouch) * sondName.rightValueParam,
                0,
                0,
                RATE_DEF
            )
        }
        return null
    }

    fun stopAllSounds() {
        for (streamId in mStreamListWithKeys) {
            if (streamId.stream != null) {
                mSoundPool.stop(streamId.stream)
            }
        }
        mSoundPool.autoPause()
    }

    fun randomTouchEvent(instrumentKeyParams: InstrumentKeyParams, timeTouch: Long) {
        for (streamParam in mStreamListWithKeys) {
            if (streamParam.instrumentKeyParams.equals(instrumentKeyParams)) {
                touchSoundEvent(
                    timeTouch,
                    streamParam.stream,
                    streamParam.stream,
                    streamParam.instrumentKeyParams.keyParams
                )
            }
        }
    }
}