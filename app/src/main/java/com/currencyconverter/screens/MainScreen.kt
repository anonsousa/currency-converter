package com.currencyconverter.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.currencyconverter.R
import com.currencyconverter.model.Currency
import com.currencyconverter.model.CurrencyTypes
import com.currencyconverter.service.CurrencyFactory

import com.currencyconverter.ui.theme.BtnColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val currencyTypes = CurrencyTypes()

    val context = LocalContext.current
    val currencies = currencyTypes.currencies
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }

    var currencyState by remember {
        mutableStateOf(listOf<Currency>())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = ColorPainter(BtnColor),
            contentDescription = "Background Image"
        )
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(8.dp),
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 45.dp)
            .align(alignment = Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Select Exchange",
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                TextField(
                    value = selectedCurrency,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    Modifier.background(BtnColor)
                ) {
                    currencies.forEach { item ->
                        DropdownMenuItem(text ={ Text(text = item) }, onClick = {
                            selectedCurrency = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }

            ElevatedButton(onClick = {
                var selected = selectedCurrency.split(" ")[0]

                var call = CurrencyFactory().getCurrencyService().getCurrency(
                    currency = selected
                )
                call.enqueue(object : Callback<List<Currency>>{
                    override fun onResponse(
                        call: Call<List<Currency>>,
                        response: Response<List<Currency>>
                    ) {
                        if(response.isSuccessful){
                            currencyState = response.body() ?: emptyList()
                        } else {
                            Log.e("API_ERROR", "Error: ${response.code()}")
                        }
                    }
                    override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                        Log.i("TEST", "onFailure: ${t.message}")
                    }

                })
            },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "Find Exchange")
            }
            LazyColumn() {
                items(currencyState){
                    CardCurrency(it)
                }
            }
        }
    }
}

@Composable
fun CardCurrency(currency: Currency){
        Card (modifier = Modifier
            .padding(top = 20.dp)
            .width(400.dp)
            .height(482.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Icon(
                        painter = painterResource(id = R.drawable.coins_solid),
                        contentDescription = "coin",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "From : ${currency.code}\n To : ${currency.codein}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,){
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_up_wide_short_solid),
                        contentDescription = "coin",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "High value : ${currency.high}",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(25.dp)
                    )
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_down_wide_short_solid),
                        contentDescription = "coin",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "Low value : ${currency.low}",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 25.dp)
                    )
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(id = R.drawable.cart_shopping_solid),
                        contentDescription = "coin",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "Bid : ${currency.bid}",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(25.dp)
                    )
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(id = R.drawable.hand_holding_dollar_solid),
                        contentDescription = "coin",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "Ask : ${currency.ask}",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 25.dp)
                    )
                }
                Row (modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(id = R.drawable.percent_solid),
                        contentDescription = "coin",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "pctChange : ${currency.pctChange}",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(25.dp)
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Column (modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ){
                    Text(
                        text = "Last Change",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 25.dp)
                    )
                    Text(
                        text = "${currency.create_date}",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 25.dp)
                    )
                }
            }
        }
    }

