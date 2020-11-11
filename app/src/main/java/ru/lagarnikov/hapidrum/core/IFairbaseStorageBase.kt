package ru.lagarnikov.hapidrum.core

import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Deferred
import java.io.File

interface IFairbaseStorageBase {

    suspend fun loadSound(
        instrumentSound: InstrumentSound,
        file: File,
        storage: FirebaseStorage
    ): Deferred<FileDownloadTask>
}