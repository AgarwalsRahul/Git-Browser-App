package com.example.gitbrowser.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


     fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    fun displaySnackBar(message:String){
        val snackbar = Snackbar.make(
            findViewById(R.id.main_container),
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}