package ru.lagarnikov.hapidrum.core.sound_loader

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import ru.lagarnikov.hapidrum.BuildConfig
import ru.lagarnikov.hapidrum.MyApp
import ru.lagarnikov.hapidrum.core.fairbase_storage.IFairbaseStorageBase
import ru.lagarnikov.hapidrum.model.InstrumentSound
import java.io.File
import java.lang.RuntimeException

class SoundLoaderUseCase(
    val sharedPrefHelper: ISharedPrefHelper,
    val fairbaseStorageBase: IFairbaseStorageBase
) : ISoundLoaderUseCase {

    private val PAUSE_TIME = 100L
    override val isLoaded = MutableLiveData<Boolean>()

    override suspend fun loadSounds(
        insrtument: Instruments,
        fileDir: File,
        storage: FirebaseStorage
    ) {
        var listState = insrtument.sounds.size
        for (instrumentSound in insrtument.sounds) {
            startLoad(instrumentSound, storage, fileDir).addOnSuccessListener {
                listState--
                Log.d("asqs", "yyy " + instrumentSound.instrumentName)
            }.addOnFailureListener {
                MyApp.crashlytics.recordException(RuntimeException("loadSounds Failure instrumentSound - " + instrumentSound.instrumentName))
            }
        }
        while (listState != 0) {
            Thread.sleep(PAUSE_TIME)
        }
        sharedPrefHelper.saveBoolean(BuildConfig.VERSION_NAME + insrtument.sounds.get(0).instrumentName, true)
    }

    private suspend fun startLoad(
        instrumentSound: InstrumentSound,
        storage: FirebaseStorage,
        fileDir: File
    ): FileDownloadTask {
        return fairbaseStorageBase.loadSound(
            instrumentSound,
            File(fileDir, instrumentSound.fileLocalName + instrumentSound.fileExtension),
            storage
        )
    }

    override suspend fun loadSounds(soundFon: SoundFons, fileDir: File, storage: FirebaseStorage) {
        var listState = soundFon.sounds.size
        for (instrumentSound in soundFon.sounds) {
            startLoad(instrumentSound, storage, fileDir).addOnSuccessListener {
                listState--
            }.addOnFailureListener {
            }
        }
        while (listState != 0) {
            Thread.sleep(PAUSE_TIME)
        }
        sharedPrefHelper.saveBoolean(BuildConfig.VERSION_NAME + soundFon.sounds.get(0).instrumentName, true)
    }
}