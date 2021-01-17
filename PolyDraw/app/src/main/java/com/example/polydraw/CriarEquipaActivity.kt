package com.example.polydraw

import android.content.Intent
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

private const val TAG: String = "ServerSocket"
private const val SERVER_PORT = 8000

class CriarEquipaActivity : AppCompatActivity() {

    private var serverSocket:ServerSocket? = null

    private lateinit var threads:ArrayList<Thread>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_equipa)


        startServer()
        var ip = getIP()
        var tvip: TextView = findViewById(R.id.tvIP)
        tvip.text = ip


    }



    fun onCriar(view: View) {
        val intent = Intent (this, EsperaActivity::class.java)
        startActivity(intent)
    }


    fun onVoltar(view: View) {
        finish()
    }






    private fun getIP():String{
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ip = wifiManager.connectionInfo.ipAddress

        return String.format("%d.%d.%d.%d",
            ip and 0xff,
            (ip shr 8) and 0xff,
            (ip shr 16) and 0xff,
            (ip shr 24) and 0xff
        )
    }

    private fun startServer(){

        thread {
            serverSocket = ServerSocket(SERVER_PORT)
            Log.d(TAG, "Socket created")
            serverSocket?.apply {
                try {
                    connectSocket(serverSocket!!.accept())
                } catch (_: Exception) {
                    Log.d(TAG, "Error accepting conection")
                    //connectionState.postValue(ConnectionState.CONNECTION_ERROR)
                } finally {
                    serverSocket?.close()
                    serverSocket = null
                    Log.d(TAG, "Socket closed")
                }
            }
        }
    }

    private fun connectSocket(socket: Socket?) {
        socket ?: return

        threads.add(thread {

            val message = socket.getInputStream().bufferedReader().readLine()
            Log.d(TAG, message)

         })
    }


}