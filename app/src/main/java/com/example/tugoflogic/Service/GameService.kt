package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.Game
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException


class GameService(context: Context) {
    private val client = OkHttpClient()
    private var context: Context;
    var listLiveData = MutableLiveData<List<Game>>()
    var newGameLiveData = MutableLiveData<Int>();

    init {
        this.context = context;
    }

    @Throws(IOException::class)
    fun findAll() {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "games")
                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<Game> = Helper.fromJson(jsonText);
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

    @Throws(IOException::class)
    fun create(id: Int) {
        Thread {
            println("============= new game")
            val json = Game.toNewObject(id);

            val body: RequestBody = RequestBody.create(
                MediaType.parse("application/json"), json
            )

            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "games")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println(jsonText);
                    if (jsonText.equals("inserted")) {
                        this.newGameLiveData.postValue(id);

                    }

                } catch (e: Exception) {

                }
            }

        }.start()
    }
}