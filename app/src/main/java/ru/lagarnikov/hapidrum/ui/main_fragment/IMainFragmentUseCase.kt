package ru.lagarnikov.hapidrum.ui.main_fragment

interface IMainFragmentUseCase {

    fun pressLeftNavButton(): Int
    fun pressRightNavButton(): Int
    fun getStartInstrument(): Int
}