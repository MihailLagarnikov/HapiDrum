package ru.lagarnikov.hapidrum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.RandomPlayer
import ru.lagarnikov.hapidrum.soundlayer.Sounds

class MainActivity : AppCompatActivity() {
    private val fonHolder = FonHolder()
    private val myMediaPlayer =
        MyMediaPlayer(this)
    private lateinit var loopPlayer : LoopPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSamples()
        loadMusicFon()
        loadRandomgenerator()
    }

    private fun loadMusicFon(){
        image_music_fon.setOnClickListener {
            (it as ImageView)
                .setImageResource(if (myMediaPlayer.startMusick()) R.drawable.btn_music_fon
                else R.drawable.btn_music_fon_off)
        }
    }

    private fun  loadSamples(){

        loopPlayer = LoopPlayer(
            applicationContext,
            findViewById(R.id.content)
        )

        loopPlayer.setViewSoundListener(button_zero,
            Sounds.ZERO
        )
        loopPlayer.setViewSoundListener(button_a,
            Sounds.A
        )
        loopPlayer.setViewSoundListener(button_b,
            Sounds.B
        )
        loopPlayer.setViewSoundListener(button_c,
            Sounds.C
        )
        loopPlayer.setViewSoundListener(button_d,
            Sounds.D
        )
        loopPlayer.setViewSoundListener(button_e,
            Sounds.E
        )
        loopPlayer.setViewSoundListener(button_f,
            Sounds.F
        )
        loopPlayer.setViewSoundListener(button_g,
            Sounds.G
        )
        loopPlayer.setViewSoundListener(button_h,
            Sounds.H
        )
        stop_all.setOnClickListener { loopPlayer.stopAllSounds() }
        image_fon.setOnClickListener {fonHolder.setFonFor(fon_image) }
    }

    private fun loadRandomgenerator(){
        val randomGenerator = RandomPlayer(loopPlayer,
            button_a,
            button_b,
            button_c,
            button_d,
            button_e,
            button_f,
            button_g,
            button_h, button_zero)
        image_random.setOnClickListener {
            image_random.setImageDrawable(
                resources.getDrawable(if (randomGenerator.pressGenerator()) R.drawable.auto_pressed else R.drawable.auto)
            )
        }
    }

}
