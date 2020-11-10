package ru.lagarnikov.hapidrum.core

import com.google.firebase.storage.FirebaseStorage
import java.io.File

interface IFairbaseStorageBase {

    fun loadSound(
        instrumentSound: InstrumentSound,
        file: File,
        storage: FirebaseStorage,
        loadListener: LoadListener
    )
}