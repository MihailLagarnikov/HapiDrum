package ru.lagarnikov.hapidrum

import android.view.View
import android.os.Handler
import android.view.MotionEvent
import ru.lagarnikov.hapidrum.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.soundlayer.TouchParam
import java.util.*
import kotlin.collections.ArrayList

class RandomPlayer(val loopPlayer: LoopPlayer,vararg views: View) {
    private val MAX_TIME = 3000

    private val handler = Handler()
    private val viewList = ArrayList<View>()
    private val random = Random()
    init {
        viewList.addAll(views)
    }
    private lateinit var runnable: Runnable
    private var isPressed = false

    fun pressGenerator(): Boolean{
        isPressed = !isPressed
        if (isPressed){
            startRandomMusick()
        }else{
            stopRandomMusic()
        }
        return isPressed
    }

    private fun startRandomMusick(){
        runnable = createRandomRunable()
        handler.postDelayed(runnable,
            random.nextInt(MAX_TIME).toLong()
        )
    }

    private fun stopRandomMusic(){
        handler.removeCallbacks(runnable)
    }

    private fun createRandomRunable(): Runnable{
        return Runnable {
            loopPlayer.randomTouchEvent(viewList.get(random.nextInt(viewList.size)),
                random.nextInt(TouchParam.MAX_TOUCH_TIME).toLong())

            startRandomMusick()
        }
    }

}