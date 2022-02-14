package com.lira.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lira.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMI)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }

        binding?.toolbarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeMetricUnitsViewVisible()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.rbMetricUnits)
                makeMetricUnitsViewVisible()
            else
                makeUSUnitsViewVisible()
        }

        binding?.btnCalculateBMI?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true

        if(binding?.etUSUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etUSUnitHeightFeet?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etUSUnitHeightInch?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            }else{
                Toast.makeText(this, "Please enter valid entries", Toast.LENGTH_SHORT).show()
            }
        }else{
            if(validateUSUnits()){
                val heightFeet: String = binding?.etUSUnitHeightFeet?.text.toString()
                val heightInch: String = binding?.etUSUnitHeightInch?.text.toString()
                val weightPounds: Float = binding?.etUSUnitWeight?.text.toString().toFloat()

                val usHeightValue = heightInch.toFloat() + heightFeet.toFloat() * 12

                val bmi = 703 * (weightPounds / (usHeightValue * usHeightValue))

                displayBMIResult(bmi)

            }else{
                Toast.makeText(this, "Please enter valid entries", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! Take care!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Looks like you're in a good shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! Take care!"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Moderately obese"
            bmiDescription = "Oops! Eat healthy food! Workout!"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Severely obese"
            bmiDescription = "Oops! Eat healthy food! Workout!"
        }else{
            bmiLabel = "Very severely obese"
            bmiDescription = "Oops! Eat healthy food! Workout!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
    }

    private fun makeMetricUnitsViewVisible(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUSUnitWeight?.visibility = View.GONE
        binding?.tilUSUnitHeightFeet?.visibility = View.GONE
        binding?.tilUSUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.etMetricUnitHeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeUSUnitsViewVisible(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUSUnitWeight?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUSUnitWeight?.text!!.clear()
        binding?.etUSUnitHeightFeet?.text!!.clear()
        binding?.etUSUnitHeightInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}