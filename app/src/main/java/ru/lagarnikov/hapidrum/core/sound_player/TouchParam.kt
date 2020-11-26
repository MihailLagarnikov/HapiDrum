package ru.lagarnikov.hapidrum.core.sound_player

class TouchParam {
    companion object{
        @JvmField
        val MAX_TOUCH_TIME = 200
    }
    private val LITLE_VALUE = 0.3f
    private val MIDLE_VALUE = 0.6f
    private val MAX_VALUE = 1f

    private val TIME_LITLE = 50L
    private val TIME_MIDLE = 120L

    fun getTouchChangeValue(timeTouch: Long?): Float{
        if (timeTouch == null) return 1f
        return when{
            timeTouch <= TIME_LITLE -> LITLE_VALUE
            timeTouch <= TIME_MIDLE -> MIDLE_VALUE
            else -> MAX_VALUE
        }
    }


}