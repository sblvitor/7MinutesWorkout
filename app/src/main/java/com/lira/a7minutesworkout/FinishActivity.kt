package com.lira.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.lira.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

        val historyDao = (application as WorkoutApp).db.historyDao()
        addDateToDatabase(historyDao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){

        val c = Calendar.getInstance()
        val dateTime = c.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}