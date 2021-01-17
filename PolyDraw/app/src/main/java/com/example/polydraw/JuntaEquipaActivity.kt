package com.example.polydraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.PrintStream
import java.net.Socket
import kotlin.concurrent.thread

private const val TAG: String = "ClientSocket"
private const val SERVER_PORT = 8000


class JuntaEquipaActivity : AppCompatActivity() {

    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_junta_equipa)

        startClient("10.0.2.16")
    }



    private fun startClient(serverIP: String, serverPort: Int = SERVER_PORT) {
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



        socket?.getOutputStream()?.run {
            thread {
                try {
                    val printStream = PrintStream(this)
                    printStream.println("Mensagem")
                    printStream.flush()

                } catch (e: Exception) {
                    Log.d(TAG, "Error init socket")
                    Log.d(TAG, e.stackTrace.toString())

                }
            }
        }
    }
}