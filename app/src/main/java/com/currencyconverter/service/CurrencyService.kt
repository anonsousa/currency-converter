package com.currencyconverter.service

import com.currencyconverter.model.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyService {
    //https://economia.awesomeapi.com.br/json/USD-BRL
    @GET("{currency}")
    fun getCurrency(
        @Path("currency") currency: String
    ): Call<List<Currency>>
}