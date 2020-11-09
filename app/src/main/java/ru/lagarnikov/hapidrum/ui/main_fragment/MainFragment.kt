package ru.lagarnikov.hapidrum.ui.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_fragment.*
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.RandomPlayer
import ru.lagarnikov.hapidrum.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.soundlayer.Sounds
import ru.lagarnikov.hapidrum.ui.FonHolder

class MainFragment: Fragment() {

    private val fonHolder = FonHolder()
    private lateinit var loopPlayer : LoopPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSamples()
        loadMusicFon()
        loadRandomgenerator()
    }

    private fun loadMusicFon(){
        image_music_fon.setOnClickListener {
            (it as ImageView)
                .setImageResource(if (MyMediaPlayer(requireContext()).startMusick()) R.drawable.btn_music_fon
                else R.drawable.btn_music_fon_off)
        }
    }

    private fun  loadSamples(){

        loopPlayer = LoopPlayer(
            requireContext(),
            requireView().findViewById(R.id.container)
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