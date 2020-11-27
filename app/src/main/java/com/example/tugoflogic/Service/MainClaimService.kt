package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.Game
import com.example.tugoflogic.models.MainClaim
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class MainClaimService(context: Context) {
    private val client = OkHttpClient()

    val listLiveData = MutableLiveData<List<MainClaim>>()
    var newMainClaimData = MutableLiveData<Int>();

    private var context: Context;

    init {
        this.context = context;
    }

    @Throws(IOException::class)
    fun findAll() {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "mainclaims")

                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<MainClaim> = Helper.fromJson(jsonText);
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
    fun create(id: Int, gameid: Int, statement: String) {
        Thread {
            println("============= new MainClaim")
            val json = MainClaim.toNewObject(id,gameid,statement);

            println(json)
            val body: RequestBody = RequestBody.create(
                MediaType.parse("application/json"), json
            )

            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "mainclaims")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println(jsonText);
                    if (jsonText.equals("inserted")) {
                        this.newMainClaimData.postValue(id);

                    }

                } catch (e: Exception) {

                }
            }

        }.start()
    }
}