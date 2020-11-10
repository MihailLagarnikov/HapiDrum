package ru.lagarnikov.hapidrum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
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
    }

    private fun setStartFragment(fragmentId: Int) {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment?
        val navController = navHost?.navController
        val navInflater = navController?.navInflater
        val graph = navInflater?.inflate(R.navigation.nav_graph)
        graph?.startDestination = fragmentId
        navController?.graph = graph!!
    }

    private fun loadSound() {
        for (instrument in Instruments.values()) {
            if (!soundLoaderUseCase.isInstrumentSoundLoaded(instrument.name)) {
                soundLoaderUseCase.loadSounds(instrument, filesDir, storage)
            }
        }
    }

}
