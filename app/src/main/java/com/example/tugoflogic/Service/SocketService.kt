package com.example.tugoflogic.Service

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

class SocketService(context: Context) {
    lateinit var mSocket: Socket;
    private var context: Context;
    var liveData = MutableLiveData<Any>()

    init {
        this.context = context;

        try {
            mSocket = IO.socket(Helper.GetUrl(this.context))
            mSocket.connect()
            Toast.makeText(
                this.context,
                "connected to " + Helper.GetUrl(this.context) + " " + mSocket.connected(),
                Toast.LENGTH_SHORT
            ).show();
        } catch (e: Exception) {
        }

        mSocket.on(Socket.EVENT_CONNECT, Emitter.Listener {
//            mSocket.emit("messages", "hi")
        });

        mSocket.on("notifications", Emitter.Listener {
            val message = it[0] as String
            println(message);
            Toast.makeText(
                this.context,
                message,
                Toast.LENGTH_SHORT
            ).show();
        })
    }
}