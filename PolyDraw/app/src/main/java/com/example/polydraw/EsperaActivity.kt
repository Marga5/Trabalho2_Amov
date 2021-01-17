package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

private const val TAG = "EsperaActivity"

class EsperaActivity : AppCompatActivity() {
    lateinit var pbProgressBar : ProgressBar
    var count : Int = 0
    lateinit var timer : Timer
    lateinit var tvEquipa : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_espera)

        tvEquipa = findViewById(R.id.tvEquipa)

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

        ObservarFS()
    }


    fun onPlay(view: View) {
        val intent = Intent (this, GameActivity::class.java)
        startActivity(intent)
    }


    fun onVoltar(view: View) {
        finish()
    }

    fun ObservarFS(){
        val db = Firebase.firestore

        db.collection("Equipa").document("RvxuZ2VFhlpyjlTEESO6").addSnapshotListener{docSS, e ->
            if(e != null)
                return@addSnapshotListener

            if (docSS != null && docSS.exists()){
                val NomeEquipa = docSS.getString("Nome")
                Log.i(TAG, "$NomeEquipa")
                tvEquipa.text = "$NomeEquipa"
            }
        }
    }
}