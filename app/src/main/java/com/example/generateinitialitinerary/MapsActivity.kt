package com.example.generateinitialitinerary

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.generateinitialitinerary.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityMapsBinding.inflate(layoutInflater).root)

    }
}