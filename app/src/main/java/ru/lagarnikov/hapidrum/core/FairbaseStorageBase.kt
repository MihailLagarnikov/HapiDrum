package ru.lagarnikov.hapidrum.core

import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.File

class FairbaseStorageBase() : IFairbaseStorageBase {
    private val SLASH = "/"


    override suspend fun loadSound(
        instrumentSound: InstrumentSound,
        file: File,
        storage: FirebaseStorage
    ): Deferred<FileDownloadTask> {
        val storageRef = storage.reference.child(
            createRefString(
                instrumentSound.instrumentName,
                instrumentSound.soundName
            )
        )
        return GlobalScope.async { storageRef.getFile(file) }
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