package ru.lagarnikov.hapidrum.core.sound_loader

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import ru.lagarnikov.hapidrum.core.fairbase_storage.IFairbaseStorageBase
import ru.lagarnikov.hapidrum.model.InstrumentSound
import java.io.File

class SoundLoaderUseCase(
    val sharedPrefHelper: ISharedPrefHelper,
    val fairbaseStorageBase: IFairbaseStorageBase
) : ISoundLoaderUseCase {

    private val PAUSE_TIME = 100L
    override val isLoaded = MutableLiveData<Boolean>()
    var currentInstrument = ""

    override fun isInstrumentSoundLoaded(insrtumentName: String) =
        sharedPrefHelper.loadBoolean(insrtumentName, false)

    override suspend fun loadSounds(insrtument: Instruments, storage: FirebaseStorage) {
        currentInstrument = insrtument.sounds.get(0).instrumentName
        var listState = insrtument.sounds.size
        for (instrumentSound in insrtument.sounds) {
            startLoad(instrumentSound, storage).addOnSuccessListener {
                listState--
            }.addOnFailureListener {
            }
        }
        while (listState != 0) {
            Thread.sleep(PAUSE_TIME)
        }
        sharedPrefHelper.saveBoolean(insrtument.sounds.get(0).instrumentName, true)
    }

    private suspend fun startLoad(
        instrumentSound: InstrumentSound,
        storage: FirebaseStorage
    ): FileDownloadTask {
        val sdDir: File = Environment.getExternalStorageDirectory()

        return fairbaseStorageBase.loadSound(
            instrumentSound,
            File(sdDir, instrumentSound.fileLocalName + instrumentSound.fileExtension),
            storage
        )
    }

    override fun getLoadingInstrumentName() = currentInstrument
}