package com.currencyconverter.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.currencyconverter.R
import com.currencyconverter.ui.theme.BtnColor

@Composable
fun HomeScreen(navController: NavController){

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = ColorPainter(Color(0xFFFFFFFF)),
            contentDescription = "Background Image"
        )

        Image(modifier = Modifier.padding(top = 180.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable._9962870_6187656),
            contentDescription = "logo"
        )
        Row (modifier = Modifier.fillMaxWidth()
            .align(Alignment.Center)
            .padding(top = 180.dp),
            horizontalArrangement = Arrangement.Center

        ){
            Text(text = "Currency Converter",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 100.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center

        ) {
            ElevatedButton(onClick = {navController.navigate("main")},
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "Search for Currency")
            }
        }
    }
}
