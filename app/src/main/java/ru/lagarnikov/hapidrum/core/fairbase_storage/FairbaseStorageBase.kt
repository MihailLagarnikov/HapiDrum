package ru.lagarnikov.hapidrum.core.fairbase_storage

import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import ru.lagarnikov.hapidrum.model.InstrumentSound
import java.io.File

class FairbaseStorageBase() :
    IFairbaseStorageBase {
    private val SLASH = "/"


    override suspend fun loadSound(
        instrumentSound: InstrumentSound,
        file: File,
        storage: FirebaseStorage
    ): FileDownloadTask {
        val storageRef = storage.reference.child(
            createRefString(
                instrumentSound.instrumentName,
                instrumentSound.soundName
            )
        )
        return storageRef.getFile(file)
    }

    private fun createRefString(vararg path: String): String {
        val rezult = StringBuilder()
        for (name in path) {
            rezult.append(name)
            rezult.append(SLASH)
        }
        rezult.deleteCharAt(rezult.lastIndex)
        return rezult.toString()
    }
}