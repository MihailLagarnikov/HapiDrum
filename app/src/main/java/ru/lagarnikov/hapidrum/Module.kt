package ru.lagarnikov.hapidrum

import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.SharedPrefHelper
import org.koin.dsl.module.module
import ru.lagarnikov.hapidrum.core.fairbase_storage.FairbaseStorageBase
import ru.lagarnikov.hapidrum.core.fairbase_storage.IFairbaseStorageBase
import ru.lagarnikov.hapidrum.core.fon_holder.FonHolder
import ru.lagarnikov.hapidrum.core.fon_holder.IFonHolder
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.core.sound_loader.SoundLoaderUseCase

val appModule = module {
    single { this }

    single { FairbaseStorageBase() as IFairbaseStorageBase }
    single { SharedPrefHelper() as ISharedPrefHelper }
    single { SoundLoaderUseCase(get(), get()) as ISoundLoaderUseCase }
    single { FonHolder(get()) as IFonHolder }

}