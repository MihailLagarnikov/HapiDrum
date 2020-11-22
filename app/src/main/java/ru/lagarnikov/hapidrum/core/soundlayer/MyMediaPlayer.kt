package ru.lagarnikov.hapidrum

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import ru.lagarnikov.hapidrum.core.sound_loader.SoundFons
import ru.lagarnikov.hapidrum.model.InstrumentSound
import java.io.File

class MyMediaPlayer(val context: Context) {
    private val VOLUME = 0.05f
    private val musicList = ArrayList<InstrumentSound>()
    private var musicPosition = 0
    private var mediaPlayer: MediaPlayer? = null
    private var isPress = false
    init {
        for (soundFon in SoundFons.values()){
            for (sound in soundFon.sounds){
                musicList.add(sound)
            }
        }
    }

    fun pressFon(): Boolean{
        isPress = !isPress
        if (isPress){
            startMusick()
        }else{
            stopMusic()
        }
        return isPress
    }

    private fun startMusick(){
        val sdDir: File = Environment.getExternalStorageDirectory()
        val fileSound = File(sdDir, musicList.get(musicPosition).fileLocalName + musicList.get(musicPosition).fileExtension)
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, Uri.parse(fileSound.absolutePath))
        mediaPlayer?.isLooping = true
        mediaPlayer?.setVolume(VOLUME, VOLUME)
        mediaPlayer?.setOnPreparedListener {  mediaPlayer?.start() }
        mediaPlayer?.setOnCompletionListener { mediaPlayer?.release() }

    }

    fun stopMusic(){
        mediaPlayer?.release()
    }

    fun nextTrack(){
        musicPosition++
        if (musicPosition >= musicList.size){
            musicPosition = 0
        }
        startMusick()
    }

    fun previusTrack(){
        musicPosition--
        if (musicPosition < 0){
            musicPosition = musicList.size - 1
        }
        startMusick()
    }
    fun getTrackName(): String{
        return if (isPress){
            musicList.get(musicPosition).fileLocalName
        }else{
            "-"
        }
    }
}