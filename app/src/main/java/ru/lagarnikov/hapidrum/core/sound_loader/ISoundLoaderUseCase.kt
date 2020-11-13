package ru.lagarnikov.hapidrum.core.sound_loader

import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage

interface ISoundLoaderUseCase {
    val isLoaded: MutableLiveData<Boolean>
    suspend fun loadSounds(insrtument: Instruments, storage: FirebaseStorage)
    fun isInstrumentSoundLoaded(insrtumentName: String): Boolean
    fun getLoadingInstrumentName(): String
}