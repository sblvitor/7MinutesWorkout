package com.lira.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.lira.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   // "Pega esse arquivo xml activity main, infla ele com o layout inflater e armazena nesse objeto de biding"
        //setContentView(R.layout.activity_main)
        setContentView(binding?.root)                           // Mesma coisa que o de cima, o root do objeto é nesse caso o Constraint Layout

        // Não precisa mais criar variáveis para todos os itens de UI
        // O objeto binding possui o xml inteiro, incluindo todos os ID's
        binding?.btnStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.btnBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
    }

    // Maneira clean de evitar memory leak. Toda vez que usar binding, setar ela para nulo
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}