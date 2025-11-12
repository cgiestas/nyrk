package com.example.softkeyboard

import android.content.Context
import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val buttonEnable: Button = findViewById<Button>(R.id.button_enable_keyboard)
        buttonEnable.setOnClickListener{
            openKeyboardEnableSettings()
        }

        val buttonSelect: Button = findViewById<Button>(R.id.button_select_keyboard)
        buttonEnable.setOnClickListener{
            openKeyboardSelectDialog()
        }
    }

    private fun openKeyboardEnableSettings(){
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
    }

    private fun openKeyboardSelectDialog(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showInputMethodPicker()
    }
}