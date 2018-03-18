package org.freactive.flawnkid

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject


/**
 * Created by akshat on 18/3/18.
 */

fun fetchLatestData(adapter: FeedsAdapter){
    val updatesList: MutableList<Feeds> = mutableListOf()
    async(UI) {
         val client = OkHttpClient()
         val request = Request.Builder()
                 .url("https://newsapi.org/v2/top-headlines?sources=national-geographic&apiKey=c677a7e57fe74e35b72085114a01bb9b")
                 .build()
        var response: Response
        val job = async(CommonPool) {
            response = client.newCall(request).execute()
            //Log.d("akshat", JSONObject(response.body()?.string()).getJSONArray("articles").toString())

            try {
                if (response.isSuccessful) {

                    val body = JSONObject(response.body()?.string()).getJSONArray("articles")
                    var i = 0
                    while (i < body.length()) {
                        val childObj = body.getJSONObject(i)
                        if (childObj != null) {
                            updatesList.add(Feeds(Source("",""),childObj.getString("author"),childObj.getString("title"),childObj.getString("description"),"",childObj.getString("urlToImage"),""))
                        }
                        i++
                        if(i==7) break
                    }
                }
            } catch (e: Exception) {

            }

        }
        job.await()
        adapter.update(updatesList)

     }




}
    /*doAsync {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://effervescence-17.firebaseio.com/notifications.json")
                .build()
        val response = client.newCall(request).execute()
        try {
            if (response.isSuccessful) {
                val updatesList: ArrayList<Notification> = ArrayList()
                val body = JSONObject(response.body()?.string())
                val keys = body.keys()

                while (keys.hasNext()) {*/