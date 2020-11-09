package ru.lagarnikov.hapidrum.soundlayer

import android.content.Context
import android.media.SoundPool
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import ru.lagarnikov.hapidrum.repositoriy.LoopRepositoriy
import ru.lagarnikov.hapidrum.ui.AnimTouch
import java.util.*
import kotlin.collections.ArrayList

class LoopPlayer(context: Context, val viewGroup: ViewGroup) {
    private val MAX_STREAM = 30
    private val PRIORITY = 1
    private val LEFT_VALUME_DEF = 1f
    private val RIGHT_VALUME_DEF = 1f
    private val RATE_DEF = 1f
    private val loopRepositoriy = LoopRepositoriy()
    private val touchParam = TouchParam()
    private val streamList = ArrayList<Int?>()
    private val animHandleStop = AnimTouch()
    private val viewList = ArrayList<View>()

    private val mSounPool = SoundPool.Builder()
        .setMaxStreams(MAX_STREAM)
        .build()

    init {
        for (loop in loopRepositoriy.getLoopList()){
            streamList.add(mSounPool.load(context, loop, PRIORITY))
        }
    }

    fun setViewSoundListener(viewSound: View, sondName: Sounds){
        viewList.add(viewSound)
        val anim = AnimTouch()
        val sound = streamList.get(loopRepositoriy.getSoundNumberInList(sondName))
        var stream: Int? = null

        var startTime = 0L
        viewSound.setOnTouchListener { v, event ->
            val calendar = Calendar.getInstance()
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    startTime = calendar.time.time
                    anim.startAnimSound(sondName, viewGroup)
                }
                MotionEvent.ACTION_UP ->{
                    val stopTime = calendar.time.time
                    stream = touchSoundEvent(stopTime - startTime, sound, stream, sondName)
                    anim.stopAnimSound()
                }

            }
            true
        }
    }

    private fun touchSoundEvent(timeTouch: Long, soundId: Int?, streamId: Int?, sondName: Sounds): Int?{
        soundId?.run{
            if (streamId != null) {
                mSounPool.stop(streamId)
            }
            mSounPool.autoResume()
            return mSounPool.play(soundId,
                LEFT_VALUME_DEF * touchParam.getTouchChangeValue(timeTouch) * sondName.leftValueParam,
                RIGHT_VALUME_DEF* touchParam.getTouchChangeValue(timeTouch) * sondName.rightValueParam,
                0,
                0,
                RATE_DEF) }
        return null
    }

    fun stopAllSounds(){
        for (streamId in streamList){
            if (streamId != null) {
                mSounPool.stop(streamId)
            }
        }
        mSounPool.autoPause()
    }

    fun randomTouchEvent(view: View, timeTouch: Long){
       val ass = touchSoundEvent(timeTouch, streamList.get(loopRepositoriy.getSoundNumberInList(loopRepositoriy.getSoundNameFromViewId(view.id))),
           getStrimId(view),
           loopRepositoriy.getSoundNameFromViewId(view.id))
    }

    private fun getStrimId(view: View): Int?{
        var i = 0
        while( i < loopRepositoriy.getLoopList().size){

            if(loopRepositoriy.getLoopList().get(i) == view.id){
                return streamList.get(i)
            }
            i++
        }
        return null
    }


}