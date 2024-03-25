package com.currencyconverter.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyFactory {
    private val url = "https://economia.awesomeapi.com.br/json/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCurrencyService(): CurrencyService {
        return retrofitFactory.create(CurrencyService::class.java)
    }
}