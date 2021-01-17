package com.example.polydraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.polydraw.connection.Cliente
import com.example.polydraw.connection.Server
import java.io.PrintStream
import java.net.Socket
import kotlin.concurrent.thread


private const val TAG: String = "ClientSocket"

class JuntaEquipaActivity : AppCompatActivity() {

    var cliente: Cliente = Cliente()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_junta_equipa)

        cliente.startClient("10.0.2.2")

        Log.d(TAG, cliente.getSocket()?.isBound.toString())
    }




}