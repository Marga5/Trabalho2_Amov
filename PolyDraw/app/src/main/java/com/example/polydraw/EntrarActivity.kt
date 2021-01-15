package com.example.polydraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class EntrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrar)
    }

    fun onVoltar(view: View) {
        finish()
    }
    fun onCriar(view: View) {}
    fun onJuntar(view: View) {}
}