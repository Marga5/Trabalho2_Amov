package com.example.polydraw.connection

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.PrintStream
import java.net.Socket
import kotlin.concurrent.thread


private const val TAG: String = "ClientSocket"
private const val SERVER_PORT = 8000

class Cliente {

    private var socket: Socket? = null


    fun startClient(serverIP: String, serverPort: Int = SERVER_PORT) {
        if (socket != null)
            return

        thread {
            try {
                val newsocket = Socket(serverIP, serverPort)
                Log.d(TAG,"Socket created")
            } catch (e: Exception) {
                Log.d(TAG, "Error creating socket")
                e.printStackTrace()
            }
        }

    }


    fun getSocket(): Socket? {
        return socket
    }

    fun enviaMensagem(){

        Log.d(TAG,"A enviar Mensagem")
        socket?.getOutputStream()?.run {
            thread {
                try {
                    val printStream = PrintStream(this)
                    printStream.println("Mensagem")
                    printStream.flush()
                    Log.d(TAG,"Mensagem enviada")
                } catch (e: Exception) {
                    Log.d(TAG, "Error init socket")
                    Log.d(TAG, e.stackTrace.toString())

                }
            }
        }
    }
}