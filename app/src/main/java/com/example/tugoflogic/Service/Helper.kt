package com.example.tugoflogic.Service

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream


class Helper {
    companion object {
        inline fun <reified T> fromJson(json: String): T {
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
//        inline fun <reified T> toJson(json: String): T {
//            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
//        }
        fun GetUrl(context: Context): String? {
            var string: String? = ""
            try {
                val inputStream: InputStream = context.assets.open("Source.txt")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                string = String(buffer)
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }

            return string
        }
    }
}