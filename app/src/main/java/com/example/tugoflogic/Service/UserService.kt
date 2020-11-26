package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.Service.Helper.Companion.GetUrl
import com.example.tugoflogic.models.User
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class UserService {
    private val client = OkHttpClient()

    val listLiveData = MutableLiveData<List<User>>()

    @Throws(IOException::class)
    fun findAll(url: String?, context: Context) {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(GetUrl(context) + url)
                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<User> = Helper.fromJson(jsonText);
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

//    @Throws(IOException::class)
//    fun postAll(url: String?, context: Context) {
//        Thread {
//            println("============= call api")
//            val request: Request = Request.Builder()
//                .url(url)
//                .build()
//            client.newCall(request).execute().use { response ->
//                try {
//                    var jsonText = response.body()!!.string();
//                    println("====" + jsonText)
//
//                    var list: List<User> = Helper.fromJson(jsonText);
//                    println("====datalist===" + list.size)
//                    for (item in list) {
//                        println(item.toString())
//                    }
//                    listLiveData.postValue(list);
//                } catch (e: Exception) {
//
//                }
//
//            }
//        }.start()
//
//    }
}