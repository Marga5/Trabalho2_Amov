package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import java.util.*

class EsperaActivity : AppCompatActivity() {
    lateinit var pbProgressBar : ProgressBar
    var count : Int = 0
    lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espera)

        pbProgressBar = findViewById(R.id.pbProgressbar)
        timer = Timer()
        val timerTask : TimerTask = object : TimerTask() {
            override fun run(){
                count++;
                pbProgressBar.setProgress(count)
                if(count == 100){
                    timer.cancel()
                }
            }
        }
        timer.schedule(timerTask, 0, 100)
    }


    fun onPlay(view: View) {
        val intent = Intent (this, GameActivity::class.java)
        startActivity(intent)
    }


    fun onVoltar(view: View) {
        finish()
    }
}