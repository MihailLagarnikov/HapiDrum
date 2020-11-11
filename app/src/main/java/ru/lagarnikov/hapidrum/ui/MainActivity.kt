package ru.lagarnikov.hapidrum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.core.sound_loader.Instruments

class MainActivity : AppCompatActivity() {

    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(
        ISoundLoaderUseCase::class.java
    )
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        storage = FirebaseStorage.getInstance()
        GlobalScope.launch{
            loadSound()
        }
    }



    private suspend fun loadSound() {
        for (instrument in Instruments.values()) {
            if (!soundLoaderUseCase.isInstrumentSoundLoaded(instrument.name)) {
                val job = GlobalScope.async{
                    soundLoaderUseCase.loadSounds(instrument, filesDir, storage)
                }
                job.await()
            }
        }
    }

}
