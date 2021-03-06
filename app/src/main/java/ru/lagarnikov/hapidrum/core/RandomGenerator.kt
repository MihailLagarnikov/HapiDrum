package ru.lagarnikov.hapidrum.core

import android.os.Handler
import ru.lagarnikov.hapidrum.core.sound_player.LoopPlayer
import ru.lagarnikov.hapidrum.core.sound_player.TouchParam
import ru.lagarnikov.hapidrum.model.InstrumentKeyParams
import java.util.*
import kotlin.collections.ArrayList

class RandomGenerator(
    val loopPlayer: LoopPlayer,
    val instrumentKeyParamsList: ArrayList<InstrumentKeyParams>
) {
    private val MAX_TIME = 3000

    private val handler = Handler()
    private val random = Random()
    private var runnable: Runnable? = null

    fun press(isOn: Boolean) {
        if (isOn) {
            startRandomMusick()
        } else {
            stopRandomMusic()
        }
    }

    private fun startRandomMusick() {
        runnable = createRandomRunable()
        handler.postDelayed(
            runnable,
            random.nextInt(MAX_TIME).toLong()
        )
    }

    private fun stopRandomMusic() {
        if (runnable != null) {
            handler.removeCallbacks(runnable)
        }
    }

    private fun createRandomRunable(): Runnable {
        return Runnable {
            loopPlayer.randomTouchEvent(
                instrumentKeyParamsList.get(random.nextInt(instrumentKeyParamsList.size)),
                random.nextInt(TouchParam.MAX_TOUCH_TIME).toLong()
            )
            startRandomMusick()
        }
    }
}