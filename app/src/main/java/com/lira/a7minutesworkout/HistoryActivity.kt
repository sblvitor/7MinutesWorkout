package com.lira.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lira.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }

        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(historyDao)

    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllHistory().collect {
                val arraylist = ArrayList(it)
                if(arraylist.isNotEmpty()){
                    binding?.tvExercisesCompleted?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoData?.visibility = View.GONE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val historyAdapter = HistoryAdapter(arraylist)
                    binding?.rvHistory?.adapter = historyAdapter

                }else{
                    binding?.tvExercisesCompleted?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoData?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}