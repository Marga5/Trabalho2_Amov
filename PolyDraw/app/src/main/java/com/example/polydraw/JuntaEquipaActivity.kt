package com.example.polydraw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import java.io.PrintStream
import java.net.Socket
import kotlin.concurrent.thread

private const val TAG: String = "ClientSocket"
private const val SERVER_PORT = 8000


class JuntaEquipaActivity : AppCompatActivity() {

    lateinit var etIp : EditText
    private var socket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_junta_equipa)

        etIp = findViewById(R.id.etIp)
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

    fun onVoltar(view: View)
    {
        finish()
    }

    fun onJuntar(view: View) {
        var ip = etIp.text
        startClient(ip.toString())

        val intent = Intent (this, EsperaActivity::class.java)
        startActivity(intent)
    }
}