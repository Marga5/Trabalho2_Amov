package com.example.polydraw

import android.content.Intent
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.polydraw.connection.Server
import com.google.firebase.firestore.FirebaseFirestore
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

private const val TAG: String = "ServerSocket"
private const val SERVER_PORT = 8000

class CriarEquipaActivity : AppCompatActivity() {

    var server: Server = Server()

    lateinit var etNomeEquipa : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_equipa)

        etNomeEquipa = findViewById(R.id.etEquipa)

        CriarFS()

        server.startServer()

        var ip = server.getIP(this)
        var tvip: TextView = findViewById(R.id.tvIP)
        tvip.text = ip.toString()


        Log.d(TAG, server.getServerSocker()?.localPort.toString())



    }



    fun onCriar(view: View) {
        atualizarFS()
        val intent = Intent (this, EsperaActivity::class.java)
        startActivity(intent)
    }


    fun CriarFS() {
        val db = FirebaseFirestore.getInstance()

        val equipa = hashMapOf(
                "IP" to 0,
                "Membros" to 0,
                "Nome" to "xpto"
        )
        db.collection("Equipa").document("RvxuZ2VFhlpyjlTEESO6").set(equipa)
    }

    private fun atualizarFS(){
        val db = FirebaseFirestore.getInstance()

        val equipa = db.collection("Equipa").document("RvxuZ2VFhlpyjlTEESO6")

        db.runTransaction{ transition ->
            val doc = transition.get(equipa)
            val nomeEquipa = etNomeEquipa

            transition.update(equipa, "Nome", nomeEquipa)

        }
    }


    fun onVoltar(view: View) {
        finish()
    }









}