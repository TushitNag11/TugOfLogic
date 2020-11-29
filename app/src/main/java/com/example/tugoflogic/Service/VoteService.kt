package com.example.tugoflogic.Service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tugoflogic.models.MainClaim
import com.example.tugoflogic.models.Vote
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class VoteService(context: Context) {
    private val client = OkHttpClient()
    var context: Context;
    val listLiveData = MutableLiveData<List<Vote>>()
    val voteResult = MutableLiveData<String>()

    init {
        this.context = context;
    }

    @Throws(IOException::class)
    fun findAll() {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "votes")
                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    var list: List<Vote> = Helper.fromJson(jsonText);
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
    fun getVote(game_id: String, vote_type_id: String) {
        Thread {
            println("============= call api")
            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "votes/" + game_id + "/" + vote_type_id)
                .build()
            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println("====" + jsonText)

                    voteResult.postValue(jsonText);
                } catch (e: Exception) {

                }

            }
        }.start()
    }

    @Throws(IOException::class)
    fun create(id: Int, gameid: Int, voteTypeId: Int,voteFlag: Boolean, statementId: Int ) {
        Thread {
            println("============= new VOTE")
            val json = Vote.toNewObject(id,gameid, voteTypeId, voteFlag, statementId );

            println(json)
            val body: RequestBody = RequestBody.create(
                MediaType.parse("application/json"), json
            )

            val request: Request = Request.Builder()
                .url(Helper.GetUrl(this.context) + "vote")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                try {
                    var jsonText = response.body()!!.string();
                    println(jsonText);
                    if (jsonText.equals("inserted")) {
                        this.voteResult.postValue(id.toString());

                    }

                } catch (e: Exception) {

                }
            }

        }.start()
    }
}