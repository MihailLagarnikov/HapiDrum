package ru.lagarnikov.hapidrum

import android.content.Context
import android.media.MediaPlayer
import ru.lagarnikov.hapidrum.repositoriy.AudioRepositoriy

class MyMediaPlayer(val context: Context) {
    private val VOLUME = 0.05f
    private val musicList = AudioRepositoriy().listMusic
    private var musicPosition = 1
    private var mediaPlayer: MediaPlayer? = null

    fun startMusick(): Boolean{
        val pos = getMusicPosition()
        if (pos == 0){
            mediaPlayer?.release()
            return false
        }
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, musicList.get(pos))
        mediaPlayer?.isLooping = true
        mediaPlayer?.setVolume(VOLUME, VOLUME)
        mediaPlayer?.setOnPreparedListener {  mediaPlayer?.start() }
        mediaPlayer?.setOnCompletionListener { mediaPlayer?.release() }
        return true
    }

    private fun getMusicPosition(): Int{
        return if (musicPosition < musicList.size){
            musicPosition++
        }else{
            musicPosition = 0
            musicPosition
        }
    }
}