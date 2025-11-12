package com.example.softkeyboard

import android.inputmethodservice.InputMethodService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout

class SoftKeyboardService : InputMethodService() {
    private val specialKeysMap: Map<String, List<String>> = mapOf(
        "a" to listOf("ã","å","ā","ª","à","á","â","ä","æ"),
        "e" to listOf("ė","ē","ę","ê","é","è","ë"),
        "i" to listOf("į","ī","ì","ï","í","î"),
        "o" to listOf("º","õ","ō","ø","œ","ò","ö","ô","ó"),
        "u" to listOf("ū","ü","ú","û","ù"),
        "s" to listOf("ß"),
        "c" to listOf("ć","ç","č"),
        "n" to listOf("ñ")
    )

    override fun onCreateInputView(): View {
        val keyboardLayout = layoutInflater.inflate(R.layout.keyboard_interface, null)
        setupKeyListeners(keyboardLayout)
        return keyboardLayout
    }

    private fun setupKeyListeners(view: View){
        if(view is Button) {
            val keyLabel = view.text.toString()
            view.setOnClickListener {
                handleKey(keyLabel)
            }
            view.setOnLongClickListener{
                val specialChars = specialKeysMap[keyLabel]
                if(specialChars != null){
                    showSpecialKeysPopup(it, specialChars)
                    true
                }else{
                    false
                }
            }
        }else if (view is ViewGroup){
                for(i in 0 until view.childCount){
                    val child = view.getChildAt(i)
                    setupKeyListeners(child)
                }
            }
        }

    private fun handleKey(label: String){
        val inputConnection : InputConnection? = currentInputConnection
        inputConnection?.let{
            when(label){
                "⌫" -> it.deleteSurroundingText(1,0)
                "Enter" -> it.sendKeyEvent(
                    android.view.KeyEvent(
                        android.view.KeyEvent.ACTION_DOWN,
                        android.view.KeyEvent.KEYCODE_ENTER
                    )
                )
                "Space" -> it.commitText(" ", 1)
                else -> it.commitText(label,1)
            }
        }
    }

    private fun showSpecialKeysPopup(anchor: View, specialChars: List<String>){
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_container, null)
        val container = popupView.findViewById<LinearLayout>(R.id.popup_keys_container)
    }
}