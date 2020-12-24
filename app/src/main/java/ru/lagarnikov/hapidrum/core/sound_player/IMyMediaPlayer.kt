package ru.lagarnikov.hapidrum.core.sound_player

interface IMyMediaPlayer {

    fun pressSwitch(): Boolean
    fun stopMusic()
    fun nextTrack()
    fun previusTrack()
    fun getTrackName(): String
}