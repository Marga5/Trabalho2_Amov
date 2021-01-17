package com.example.polydraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.net.Socket
import kotlin.concurrent.thread

private const val TAG: String = "ClientSocket"
private const val SERVER_PORT = 8000


class JuntaEquipaActivity : AppCompatActivity() {

    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_junta_equipa)

        startClient("")
    }



    private fun startClient(serverIP: String, serverPort: Int = SERVER_PORT) {
        if (socket != null)
            return
        thread {
            try {
                val newsocket = Socket(serverIP, serverPort)
                Log.d(TAG, "Socket created")
                newsocket.getOutputStream().bufferedWriter().write("Mensagem")
            } catch (_: Exception) {
                Log.d(TAG,"Erro a iniciar cliente")
            }
        }
    }
}