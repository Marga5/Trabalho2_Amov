package com.example.polydraw.connection

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

private const val TAG: String = "ServerSocket"

class Server {
  
    private val SERVER_PORT = 8000
    private var serverSocket:ServerSocket? = null
    private lateinit var threads:ArrayList<Thread>




    fun getIP(context: Context):String{
        
        //val wifiManager = applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        val wifiManager = context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        val ip = wifiManager.connectionInfo.ipAddress

        return String.format("%d.%d.%d.%d",
                ip and 0xff,
                (ip shr 8) and 0xff,
                (ip shr 16) and 0xff,
                (ip shr 24) and 0xff
        )
    }

    fun getServerSocker(): ServerSocket? {
        return serverSocket
    }

     fun startServer(){

        thread {
            serverSocket = ServerSocket(SERVER_PORT )
            Log.d(TAG, "Socket created")
            serverSocket?.apply {
                try {
                    Log.d(TAG, "A espera que se conectem")
                    var socket = serverSocket!!.accept()
                    Log.d(TAG, "já se conectaram")

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