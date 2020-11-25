package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.Teacher
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class TeacherService {
    private val client = OkHttpClient()

    val listLiveData = MutableLiveData<List<Teacher>>()

    @Throws(IOException::class)
    fun findAll(url: String?, context: Context) {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<Teacher> = Helper.fromJson(jsonText);
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