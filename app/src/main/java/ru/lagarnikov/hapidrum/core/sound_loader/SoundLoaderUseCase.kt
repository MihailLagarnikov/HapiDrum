package ru.lagarnikov.hapidrum.core.sound_loader

import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import ru.lagarnikov.hapidrum.core.IFairbaseStorageBase
import ru.lagarnikov.hapidrum.core.InstrumentSound
import ru.lagarnikov.hapidrum.core.LoadListener
import ru.lagarnikov.hapidrum.core.StateLoad
import java.io.File

class SoundLoaderUseCase(
    val sharedPrefHelper: ISharedPrefHelper,
    val fairbaseStorageBase: IFairbaseStorageBase
) : ISoundLoaderUseCase, LoadListener {

    override val isLoaded = MutableLiveData<Boolean>()

    init {
        isLoaded.value = false
    }

    private val SOUND_DIR = "sound"
    private var successCount = 0
    private var fileRepeat: File? = null
    private var storageRepeat: FirebaseStorage? = null

    override fun isInstrumentSoundLoaded(insrtumentName: String) =
        sharedPrefHelper.loadBoolean(insrtumentName, false)

    override fun loadSounds(insrtument: Instruments, fileDir: File, storage: FirebaseStorage) {
        fileRepeat = fileDir
        storageRepeat = storage
        successCount = insrtument.sounds.size
        for (instrumentSound in insrtument.sounds) {
            startLoad(instrumentSound, fileDir, storage)
        }
    }

    private fun startLoad(
        instrumentSound: InstrumentSound,
        fileDir: File,
        storage: FirebaseStorage
    ) {
        val path = File(fileDir, SOUND_DIR)
        fairbaseStorageBase.loadSound(
            instrumentSound,
            File(path, instrumentSound.soundName),
            storage,
            this
        )
    }

    override fun setLoadState(state: StateLoad, instrumentSound: InstrumentSound) {
        if (state == StateLoad.SUCCESS) {
            successCount--
        }
        if (state == StateLoad.FAILURE && fileRepeat != null && storageRepeat != null) {
            startLoad(instrumentSound, fileRepeat!!, storageRepeat!!)
        }
        if (successCount == 0) {
            //все загрузили
            sharedPrefHelper.saveBoolean(instrumentSound.instrumentName, true)
            isLoaded.value = true
        }
    }
}