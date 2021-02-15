package ru.lagarnikov.hapidrum.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import ru.lagarnikov.hapidrum.BuildConfig
import ru.lagarnikov.hapidrum.R
import ru.lagarnikov.hapidrum.core.getFragmentIdFromLink
import ru.lagarnikov.hapidrum.core.sound_loader.ISoundLoaderUseCase
import ru.lagarnikov.hapidrum.core.sound_loader.Instruments
import ru.lagarnikov.hapidrum.core.sound_loader.SoundFons
import ru.lagarnikov.hapidrum.ui.dilogs.LoadingDialog
import ru.lagarnikov.hapidrum.ui.main_fragment.MainFragmentViewModel
import java.io.File

class MainActivity : AppCompatActivity(), FilePathLoad {

    private val PERMISSION_REQUEST_CODE = 443
    private val soundLoaderUseCase: ISoundLoaderUseCase by KoinJavaComponent.inject(
        ISoundLoaderUseCase::class.java
    )
    private val sharedPref: ISharedPrefHelper by KoinJavaComponent.inject(ISharedPrefHelper::class.java)
    lateinit var storage: FirebaseStorage
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainFragmentViewModel =
            ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        storage = FirebaseStorage.getInstance()
        sharedPref.init(getPreferences(Context.MODE_PRIVATE))
        if (!hasPermissions()) {
            requestPerms()
        } else {
            loadSound()
        }
        if (intent != null) {
            onNewIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val fragmentId = getFragmentIdFromLink(intent.data, this)
        if (intent.action.equals(Intent.ACTION_VIEW) && fragmentId != null) {
            mainFragmentViewModel.deepLinkNavigateFragment.value = fragmentId
        }
    }

    private fun loadSound() {
        val dialog = if (!sharedPref.loadBoolean(
                BuildConfig.VERSION_NAME + Instruments.values().get(0).sounds.get(0).instrumentName,
                false
            )
        ) {
            LoadingDialog()
        } else {
            null
        }
        dialog?.isCancelable = false
        dialog?.show(supportFragmentManager, "tag")
        soundLoaderUseCase.countOfSampleInstrument.value = Instruments.values().size
        var oldCount = Instruments.values().size
        var allFileLoaded = true
        for (instrument in Instruments.values()) {
            if (sharedPref.loadBoolean(
                    BuildConfig.VERSION_NAME + instrument.sounds.get(0).instrumentName,
                    false
                )
            ) {
                --oldCount
                soundLoaderUseCase.countOfSampleInstrument.value = (oldCount)
                continue
            }
            soundLoaderUseCase.isLoaded.value = false
            GlobalScope.launch(Dispatchers.Main) {
                if (!sharedPref.loadBoolean(BuildConfig.VERSION_NAME + instrument.name, false)) {
                    val job = GlobalScope.async(Dispatchers.IO) {
                        soundLoaderUseCase.loadSounds(instrument, getFileForLoading(), storage)
                    }
                    job.await()
                    allFileLoaded = false
                    --oldCount
                    soundLoaderUseCase.countOfSampleInstrument.value = (oldCount)
                    soundLoaderUseCase.isLoaded.value = true
                }
            }
        }
        soundLoaderUseCase.countOfSampleInstrument.observe(this, Observer {
            if (it <= 0 && !allFileLoaded){
                Snackbar.make(container, R.string.loading_exit, Snackbar.LENGTH_LONG).show()
            }
            if (it <= 0){
                dialog?.dismiss()
            }
        })
        loadFonMusick()
    }

    private fun loadFonMusick() {
        for (soundFon in SoundFons.values()) {
            if (sharedPref.loadBoolean(
                    BuildConfig.VERSION_NAME + soundFon.sounds.get(0).instrumentName,
                    false
                )
            ) continue
            soundLoaderUseCase.isLoaded.value = false
            GlobalScope.launch(Dispatchers.Main) {
                if (!sharedPref.loadBoolean(BuildConfig.VERSION_NAME + soundFon.name, false)) {
                    val job = GlobalScope.async {
                        soundLoaderUseCase.loadSounds(soundFon, getFileForLoading(), storage)
                    }
                    job.await()
                    sharedPref.saveBoolean(
                        BuildConfig.VERSION_NAME + soundFon.sounds.get(0).instrumentName,
                        true
                    )
                    soundLoaderUseCase.isLoaded.value = true
                }
            }
        }
    }

    private fun hasPermissions(): Boolean {
        val permissions =
            arrayOf(
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
        } else {
            Snackbar.make(container, R.string.has_not_permission, Snackbar.LENGTH_LONG)
        }
    }

    override fun getFileForLoading(): File {
        return filesDir
    }
}
