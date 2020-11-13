package ru.lagarnikov.hapidrum.core.fairbase_storage

import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import ru.lagarnikov.hapidrum.model.InstrumentSound
import java.io.File

interface IFairbaseStorageBase {

    suspend fun loadSound(
        instrumentSound: InstrumentSound,
        file: File,
        storage: FirebaseStorage
    ): FileDownloadTask
}