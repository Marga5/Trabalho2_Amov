package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CriarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar)
    }

    fun onVoltar(view: View) {
        finish()
    }

    fun onCriar(view: View) {
        val intent = Intent (this, EsperaActivity::class.java)
        startActivity(intent)
    }
}