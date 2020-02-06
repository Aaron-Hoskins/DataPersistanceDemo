package com.examples.coding.datapersistancedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.btnSaveToSharedPref -> saveValueToSharedPref(etSharedPrefInput.text.toString())
            R.id.btnRetrieveValueFromSharedPref -> getValueFromSharedPref()
            R.id.btnStartDatabaseActivity -> startActivity(Intent(this, DatabaseActivity::class.java))
        }
    }

    fun saveValueToSharedPref(valueToSave : String) {
        val sharedPref = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(STRING_KEY, valueToSave)
        editor.apply()
        Toast.makeText(this, "Value saved to shared pref", Toast.LENGTH_LONG).show()
    }

    fun getValueFromSharedPref() {
        val sharedPref = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val currentValue = sharedPref.getString(STRING_KEY, "No value currently saved")

        tvSharedPrefDisplay.text = currentValue
    }
}
