package ru.lagarnikov.hapidrum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import ru.lagarnikov.hapidrum.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

}
