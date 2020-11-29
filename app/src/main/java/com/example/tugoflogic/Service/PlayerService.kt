package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.Player
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class PlayerService(context: Context) {
    private var client = OkHttpClient()
    private var context: Context;
    val listLiveData = MutableLiveData<List<Player>>()

    init {
        this.context = context;
    }

    @Throws(IOException::class)
    fun findAll(context: Context) {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "votes")
                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<Player> = Helper.fromJson(jsonText);
                    println("====datalist===" + list.size)
                    for (item in list) {
                        println(item.toString())
                    }
                    listLiveData.postValue(list);
                } catch (e: Exception) {

                }

            }
        }.start()

    }
}