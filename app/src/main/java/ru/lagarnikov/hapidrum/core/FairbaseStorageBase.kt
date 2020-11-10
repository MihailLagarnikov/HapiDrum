package ru.lagarnikov.hapidrum.core

import com.google.firebase.storage.FirebaseStorage
import java.io.File

class FairbaseStorageBase() : IFairbaseStorageBase {
    private val SLASH = "/"


    override fun loadSound(
        instrumentSound: InstrumentSound,
        file: File,
        storage: FirebaseStorage,
        loadListener: LoadListener
    ) {
        val storageRef = storage.reference.child(
            createRefString(
                instrumentSound.instrumentName,
                instrumentSound.soundName
            )
        )
        loadListener.setLoadState(StateLoad.LOAD, instrumentSound)
        storageRef.getFile(file).addOnSuccessListener {
            loadListener.setLoadState(StateLoad.SUCCESS, instrumentSound)
        }.addOnFailureListener {
            loadListener.setLoadState(StateLoad.FAILURE, instrumentSound)
        }

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