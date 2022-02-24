package com.lira.a7minutesworkout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.lira.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    // Timers
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 10
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Permite a toolbar aparecer
        setSupportActionBar(binding?.toolbarExercise)

        // Coloca o botão de voltar
        if(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Coloca o comportamento de voltar no botão
        binding?.toolbarExercise?.setNavigationOnClickListener {
            showAreYouSureBackButtonDialog()
        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()

        setUpRestView()
        setUpExerciseStatusRecyclerView()

    }

    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setUpRestView(){

        try{
            val soundURI = Uri.parse("android.resource://com.lira.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.ivExerciseImg?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseProgressBar?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvNextExercise?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(restTimerDuration * 1000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpExerciseView(){
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.ivExerciseImg?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseProgressBar?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.INVISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivExerciseImg?.setImageResource(exerciseList!![currentExercisePosition].getImg())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding?.exerciseProgressBar?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(exerciseTimerDuration * 1000, 1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = 30 - exerciseProgress
                binding?.tvExerciseTimer?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter?.notifyDataSetChanged()

                if(currentExercisePosition < exerciseList!!.size - 1) {
                    setUpRestView()
                }else{
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language specified is not supported!")
            }
        }else{
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun showAreYouSureBackButtonDialog(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Are you sure?")
        builder.setMessage("This will stop your workout. Are you sure you want to quit?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { dialog, _ ->
            this@ExerciseActivity.finish()
        }
        builder.setNegativeButton("No") {dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onBackPressed() {
        showAreYouSureBackButtonDialog()
        //super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        binding = null
    }
}