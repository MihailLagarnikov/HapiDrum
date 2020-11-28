package ru.lagarnikov.hapidrum.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.twosmalpixels.travel_notes.core.repositoriy.SharedPref.ISharedPrefHelper
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.java.standalone.KoinJavaComponent
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.core.sound_loader.Instruments
import ru.lagarnikov.hapidrum.core.sound_loader.SoundFons
import ru.lagarnikov.hapidrum.ui.dilogs.LoadingDialog

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 443
    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(ISoundLoaderUseCase::class.java)
    private val sharedPref: ISharedPrefHelper by KoinJavaComponent.inject(ISharedPrefHelper::class.java)
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        storage = FirebaseStorage.getInstance()
        sharedPref.init(getPreferences(Context.MODE_PRIVATE))
        if (!hasPermissions()) {
            requestPerms()
        }else{
            loadSound()
        }
    }

    private fun loadSound() {
        val dialog = if (!sharedPref.loadBoolean(Instruments.values().get(0).sounds.get(0).instrumentName, false)) {
            LoadingDialog()
        }else{
            null
        }
        dialog?.show(supportFragmentManager, "tag")
        for (instrument in Instruments.values()) {
            if (sharedPref.loadBoolean(instrument.sounds.get(0).instrumentName, false)) continue
            soundLoaderUseCase.isLoaded.value = false
            GlobalScope.launch(Dispatchers.Main){
                if (!soundLoaderUseCase.isInstrumentSoundLoaded(instrument.name)) {
                    val job = GlobalScope.async{
                        soundLoaderUseCase.loadSounds(instrument,  storage)
                    }
                    job.await()
                    soundLoaderUseCase.isLoaded.value = true
                    dialog?.dismiss()
                    Snackbar.make(container, R.string.loading_exit, Snackbar.LENGTH_LONG).show()
                }
            }
        }
        loadFonMusick()
    }

    private fun loadFonMusick() {
        for (soundFon in SoundFons.values()) {
            if (sharedPref.loadBoolean(soundFon.sounds.get(0).instrumentName, false)) continue
            soundLoaderUseCase.isLoaded.value = false
            GlobalScope.launch(Dispatchers.Main){
                if (!soundLoaderUseCase.isInstrumentSoundLoaded(soundFon.name)) {
                    val job = GlobalScope.async{
                        soundLoaderUseCase.loadSounds(soundFon,  storage)
                    }
                    job.await()
                    soundLoaderUseCase.isLoaded.value = true
                }
            }
        }
    }

    private fun hasPermissions(): Boolean {
        val permissions =
            arrayOf (
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        for (perms in permissions) {
            val res = PermissionChecker.checkCallingOrSelfPermission(this, perms)
            if (res != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun requestPerms() {
        val permissions =
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var allowed = true
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> for (res in grantResults) {
                allowed = allowed && res == PackageManager.PERMISSION_GRANTED
            }
            else -> allowed = false
        }
        if (allowed) {
            loadSound()
        }else{
            Snackbar.make(container, R.string.has_not_permission, Snackbar.LENGTH_LONG)
        }
    }
}
