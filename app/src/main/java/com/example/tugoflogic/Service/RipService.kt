package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.MainClaim
import com.example.tugoflogic.models.Rip
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class RipService(context: Context) {
    private val client = OkHttpClient()

    val listLiveData = MutableLiveData<List<Rip>>()
    private var context: Context;

    init {
        this.context = context;
    }

    @Throws(IOException::class)
    fun findAll() {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "rips")

                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<Rip> = Helper.fromJson(jsonText);
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
    fun update(rip: Rip?) {
        Thread {
            println("============= update Rip")

            val json = rip?.toJSON();

            println(json)
            val body: RequestBody = RequestBody.create(
                MediaType.parse("application/json"), json
            )

            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "rips")
                .put(body)
                .build()

            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println(jsonText);


                } catch (e: Exception) {

                }
            }

        }.start()

    }


}