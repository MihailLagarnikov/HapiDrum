package ru.lagarnikov.hapidrum.ui.main_fragment

import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.SELECTED_INSTRUMENT_FRAGMENT
import ru.lagarnikov.hapidrum.R

class MainFragmentUseCase(val sharedPrefHelper: ISharedPrefHelper) : IMainFragmentUseCase {

    private var currentInstrumentFragment =
        sharedPrefHelper.loadInt(SELECTED_INSTRUMENT_FRAGMENT, 0)

    private val listInstruments = arrayListOf<Int>(
        R.id.mainInstrument,
        R.id.tree,
        R.id.unisonOfHearts,
        R.id.openSpace,
        R.id.budda,
        R.id.budda_test
    )

    override fun pressLeftNavButton(): Int {
        currentInstrumentFragment -= 1
        if (currentInstrumentFragment < 0) {
            currentInstrumentFragment = listInstruments.size - 1
        }
        return getStartInstrument()
    }

    override fun pressRightNavButton(): Int {
        currentInstrumentFragment += 1
        if (currentInstrumentFragment >= listInstruments.size) {
            currentInstrumentFragment = 0
        }
        return getStartInstrument()
    }

    override fun getStartInstrument(): Int {
        sharedPrefHelper.saveInt(SELECTED_INSTRUMENT_FRAGMENT, currentInstrumentFragment)
        return listInstruments.get(currentInstrumentFragment)
    }

}