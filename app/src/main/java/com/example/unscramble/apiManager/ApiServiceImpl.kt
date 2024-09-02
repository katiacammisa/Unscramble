package com.example.unscramble.apiManager

import android.content.Context
import android.widget.Toast
import com.example.unscramble.R
import com.example.unscramble.ranking.RankingModel
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getRanking(context: Context, onSuccess: (List<RankingModel>) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.ranking_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<RankingModel>> = service.getRanking()

        call.enqueue(object : Callback<List<RankingModel>> {
            override fun onResponse(response: Response<List<RankingModel>>?, retrofit: Retrofit?) {
                loadingFinished()
                if(response?.isSuccess == true) {
                    val jokes: List<RankingModel> = response.body()
                    onSuccess(jokes)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get rankings", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }
}