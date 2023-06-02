package com.example.rakaminfinaltask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rakaminfinaltask.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.actionbar_title)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_main, HomeFragment(), HomeFragment::class.java.simpleName)
            .commit()
    }
}