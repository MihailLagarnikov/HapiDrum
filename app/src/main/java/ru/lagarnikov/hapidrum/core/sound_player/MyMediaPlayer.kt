package ru.lagarnikov.hapidrum

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import ru.lagarnikov.hapidrum.core.sound_loader.SoundFons
import ru.lagarnikov.hapidrum.core.sound_player.IMyMediaPlayer
import ru.lagarnikov.hapidrum.model.InstrumentSound
import java.io.File

class MyMediaPlayer(val context: Context, val fileDir: File): IMyMediaPlayer {

    private val VOLUME = 0.05f

    private val mMusicList = ArrayList<InstrumentSound>()
    private var mMusicPosition = 0
    private var mMediaPlayer: MediaPlayer? = null
    private var isPress = false

    init {
        for (soundFon in SoundFons.values()){
            for (sound in soundFon.sounds){
                mMusicList.add(sound)
            }
        }
    }

    override fun pressSwitch(): Boolean{
        isPress = !isPress
        if (isPress){
            startMusick()
        }else{
            stopMusic()
        }
        return isPress
    }
    override fun stopMusic(){
        mMediaPlayer?.release()
    }

    override fun nextTrack(){
        mMusicPosition++
        if (mMusicPosition >= mMusicList.size){
            mMusicPosition = 0
        }
        startMusick()
    }

    override fun previusTrack(){
        mMusicPosition--
        if (mMusicPosition < 0){
            mMusicPosition = mMusicList.size - 1
        }
        startMusick()
    }

    override fun getTrackName(): String{
        return if (isPress){
            mMusicList.get(mMusicPosition).fileLocalName
        }else{
            context.resources.getString(R.string.nothing)
        }
    }

    private fun startMusick(){
        val fileSound = File(fileDir, mMusicList.get(mMusicPosition).fileLocalName + mMusicList.get(mMusicPosition).fileExtension)
        mMediaPlayer?.release()
        mMediaPlayer = MediaPlayer.create(context, Uri.parse(fileSound.absolutePath))
        mMediaPlayer?.isLooping = true
        mMediaPlayer?.setVolume(VOLUME, VOLUME)
        mMediaPlayer?.setOnPreparedListener {  mMediaPlayer?.start() }
        mMediaPlayer?.setOnCompletionListener { mMediaPlayer?.release() }
    }
}