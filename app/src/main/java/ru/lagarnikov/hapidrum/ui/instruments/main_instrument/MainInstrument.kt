package ru.lagarnikov.hapidrum.ui.instruments.main_instrument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.ChildInstrumentFragmentListener
import ru.lagarnikov.hapidrum.core.InstrumentKeyParams

class MainInstrument : Fragment(), ChildInstrumentFragmentListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_instrument_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getInstrumentParams(): ArrayList<InstrumentKeyParams> {
        //main_instrument
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}