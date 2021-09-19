package com.bignerdranch.android.coursevalute


object Common {
    private const val BASE_URL = "https://www.cbr-xml-daily.ru/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}