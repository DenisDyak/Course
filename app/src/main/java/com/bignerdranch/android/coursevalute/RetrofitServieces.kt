package com.bignerdranch.android.coursevalute

import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("daily_json.js")
    fun getValuteList(): Call<ListJson>
}