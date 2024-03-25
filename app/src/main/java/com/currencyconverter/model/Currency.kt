package com.currencyconverter.model

import java.util.Date

data class Currency(
    val code: String,
    val codein: String,
    val name: String,
    val high: String,
    val low: String,
    val varBid: String,
    val pctChange: String,
    val bid: String,
    val ask: String,
    val timestamp: String,
    val create_date: String
)
