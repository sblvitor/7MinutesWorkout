package com.lira.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lira.a7minutesworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Permite a toolbar aparecer
        setSupportActionBar(binding?.toolbarFinish)

        // Coloca o botão de voltar
        if(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.toolbarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}