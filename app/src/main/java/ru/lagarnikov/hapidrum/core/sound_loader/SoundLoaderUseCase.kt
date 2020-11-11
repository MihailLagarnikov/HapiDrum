package ru.lagarnikov.hapidrum.core.sound_loader

import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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

    private var successCount = 0
    private var fileRepeat: File? = null
    private var storageRepeat: FirebaseStorage? = null
    var currentInstrument = ""

    override fun isInstrumentSoundLoaded(insrtumentName: String) =
        sharedPrefHelper.loadBoolean(insrtumentName, false)

    override suspend fun loadSounds(insrtument: Instruments, fileDir: File, storage: FirebaseStorage){
        fileRepeat = fileDir
        storageRepeat = storage
        successCount = insrtument.sounds.size
        currentInstrument = insrtument.sounds.get(0).instrumentName
        isLoaded.value = false

        var job_1: Deferred<FileDownloadTask>? = null
        var job_2: Deferred<FileDownloadTask>? = null
        var job_3: Deferred<FileDownloadTask>? = null
        var job_4: Deferred<FileDownloadTask>? = null
        var job_5: Deferred<FileDownloadTask>? = null
        var job_6: Deferred<FileDownloadTask>? = null
        var job_7: Deferred<FileDownloadTask>? = null
        var job_8: Deferred<FileDownloadTask>? = null
        var job_9: Deferred<FileDownloadTask>? = null
        var job_10: Deferred<FileDownloadTask>? = null

            var count = 0
            for (instrumentSound in insrtument.sounds) {
                count++
                when (count){
                    1 -> job_1 = startLoad(instrumentSound, fileDir, storage)
                    2 -> job_2 = startLoad(instrumentSound, fileDir, storage)
                    3 -> job_3 = startLoad(instrumentSound, fileDir, storage)
                    4 -> job_4 = startLoad(instrumentSound, fileDir, storage)
                    5 -> job_5 = startLoad(instrumentSound, fileDir, storage)
                    6 -> job_6 = startLoad(instrumentSound, fileDir, storage)
                    7 -> job_7 = startLoad(instrumentSound, fileDir, storage)
                    8 -> job_8 = startLoad(instrumentSound, fileDir, storage)
                    9 -> job_9 = startLoad(instrumentSound, fileDir, storage)
                    10 -> job_10 = startLoad(instrumentSound, fileDir, storage)
                }
            }
        job_1?.await()?.isSuccessful
        job_2?.await()
        job_3?.await()
        job_4?.await()
        job_5?.await()
        job_6?.await()
        job_7?.await()
        job_8?.await()
        job_9?.await()
        job_10?.await()

    }

    private suspend fun startLoad(
        instrumentSound: InstrumentSound,
        fileDir: File,
        storage: FirebaseStorage
    ): Deferred<FileDownloadTask> {
        val path = File(fileDir, SOUND_DIR)
        return fairbaseStorageBase.loadSound(
            instrumentSound,
            File(path, instrumentSound.soundName),
            storage
        )
    }

    override fun setLoadState(state: StateLoad, instrumentSound: InstrumentSound) {
        if (state == StateLoad.SUCCESS) {
            successCount--
        }
        if (state == StateLoad.FAILURE && fileRepeat != null && storageRepeat != null) {
            GlobalScope.async{
                startLoad(instrumentSound, fileRepeat!!, storageRepeat!!)
            }
        }
        if (successCount == 0) {
            //все загрузили
            sharedPrefHelper.saveBoolean(instrumentSound.instrumentName, true)
            isLoaded.value = true
        }
    }

    override fun getLoadingInstrument() = currentInstrument
}