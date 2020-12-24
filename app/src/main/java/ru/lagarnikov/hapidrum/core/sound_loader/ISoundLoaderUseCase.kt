package ru.lagarnikov.hapidrum.core.sound_loader

import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import java.io.File

interface ISoundLoaderUseCase {
    val isLoaded: MutableLiveData<Boolean>
    suspend fun loadSounds(insrtument: Instruments, fileDir: File, storage: FirebaseStorage)
    suspend fun loadSounds(soundFon: SoundFons, fileDir: File, storage: FirebaseStorage)
}