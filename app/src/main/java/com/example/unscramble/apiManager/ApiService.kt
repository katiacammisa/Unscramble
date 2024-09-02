package com.example.unscramble.apiManager

import com.example.unscramble.ranking.RankingModel
import retrofit.Call
import retrofit.http.GET

interface ApiService {

    @GET("ranking")
    fun getRanking(): Call<List<RankingModel>>
}