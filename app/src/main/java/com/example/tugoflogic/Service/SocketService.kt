package com.example.tugoflogic.Service

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.ESocket
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.io.IOException

class SocketService(context: Context) {
    lateinit var mSocket: Socket;
    private var context: Context;
    var liveData = MutableLiveData<Any>()
    var message = MutableLiveData<String>()

    init {
        this.context = context;
        connect()
    }

    @Throws(IOException::class)
    fun connect() {
        try {
            mSocket = IO.socket(Helper.GetUrl(this.context))
            mSocket.connect()
        } catch (e: Exception) {
        }

        mSocket.on(Socket.EVENT_CONNECT, Emitter.Listener {
            println("connected to " + Helper.GetUrl(this.context) + " " + mSocket.connected());
        });

        mSocket.on("notifications", Emitter.Listener {
            val message = it[0] as String
            println("WS: " + message);
            this.message.postValue(message)

        })
    }

    fun sendMessage(message: String) {
        mSocket.emit("messages", message);
    }
}