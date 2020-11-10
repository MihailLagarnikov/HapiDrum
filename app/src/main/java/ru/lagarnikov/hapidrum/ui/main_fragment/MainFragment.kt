package ru.lagarnikov.hapidrum.ui.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_instrument_fragment.*
import ru.lagarnikov.hapidrum.MyMediaPlayer
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.RandomPlayer
import ru.lagarnikov.hapidrum.core.ChildInstrumentFragmentListener
import ru.lagarnikov.hapidrum.core.InstrumentKeyParams
import ru.lagarnikov.hapidrum.soundlayer.LoopPlayer
import ru.lagarnikov.hapidrum.soundlayer.Sounds
import ru.lagarnikov.hapidrum.ui.FonHolder


class MainFragment : Fragment() {

    private val fonHolder = FonHolder()
    private val loopPlayer = LoopPlayer(requireContext())
    private var childInstrumentFragmentListener: ChildInstrumentFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMusicFon()
        loadRandomgenerator()
        createInstrumentChangeObserver()
    }

    private fun createInstrumentChangeObserver() {
        val fragmentContainer = view?.findViewById<View>(R.id.instrument_container)
        val navController = Navigation.findNavController(fragmentContainer!!)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val currentFragment =
                instrument_container.childFragmentManager.fragments.firstOrNull { it.isVisible }
            if (currentFragment is ChildInstrumentFragmentListener) {
                loadSamples(currentFragment.getInstrumentParams())
            }
        }
    }

    private fun loadMusicFon() {
        image_music_fon.setOnClickListener {
            (it as ImageView)
                .setImageResource(
                    if (MyMediaPlayer(requireContext()).startMusick()) R.drawable.btn_music_fon
                    else R.drawable.btn_music_fon_off
                )
        }
    }

    private fun loadSamples(instrumentKeyParamsList: ArrayList<InstrumentKeyParams>) {

        for (params in instrumentKeyParamsList) {
            loopPlayer.setInstrumentParamsKey(params)
        }


        stop_all.setOnClickListener { loopPlayer.stopAllSounds() }
        image_fon.setOnClickListener { fonHolder.setFonFor(fon_image) }
    }

    private fun loadRandomgenerator() {
        val randomGenerator = RandomPlayer(
            loopPlayer,
            button_a,
            button_b,
            button_c,
            button_d,
            button_e,
            button_f,
            button_g,
            button_h, button_zero
        )
        image_random.setOnClickListener {
            image_random.setImageDrawable(
                resources.getDrawable(if (randomGenerator.pressGenerator()) R.drawable.auto_pressed else R.drawable.auto)
            )
        }
    }
}