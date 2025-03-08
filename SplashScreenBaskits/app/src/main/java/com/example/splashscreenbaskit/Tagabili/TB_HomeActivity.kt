package com.example.splashscreenbaskit.Tagabili

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.splashscreenbaskit.R
import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily

@Preview(showBackground = true)
@Composable
fun TB_HomeActivityPreview() {
    TB_HomeContent(navController = rememberNavController())
}

@Composable
fun TB_HomeContent(navController: NavController) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .height(363.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background (Color(0xFF1d7151))
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(45.dp))

                    Image(
                        painter = painterResource(id = R.drawable.baskitlogo_white),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(70.dp)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Shop Smarter, Not Harder",
                        fontFamily = poppinsFontFamily,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-20).dp)
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(20.dp))
                            .height(160.dp)
                            .width(330.dp)
                            .padding(start = 20.dp, end = 20.dp)
                    ){
                        Text(
                            text = "PENDING ORDERS",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier= Modifier.padding(top = 15.dp)
                        )

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){

                            Column (
                                modifier = Modifier.padding(top = 65.dp, end = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Text(
                                    text = "Dagupan",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )

                                Text(
                                    text = "0",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 36.sp,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.width(50.dp))

                            Column (
                                modifier = Modifier.padding(top = 65.dp, end = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Calasiao",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )

                                Text(
                                    text = "0",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 36.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }




        Spacer(modifier = Modifier.height(40.dp))

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 390.dp, start = 20.dp, end = 20.dp)
        ){
            Text(
                text = "ORDERS",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            DropdownLocation(navController)

            Spacer(modifier = Modifier.height(20.dp))

            //DefaultScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownLocation(navController: NavController){
    val options = listOf("Select location --", "Dagupan", "Calasiao")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth() .menuAnchor(),
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(10.dp)
        )


        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            options.forEach{
                DropdownMenuItem(
                    text = {Text(text = it,style = TextStyle(fontFamily = poppinsFontFamily))},
                    onClick = {
                        selectedOption = it
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
    when (selectedOption) {
        "Dagupan" -> DagupanOrders(navController)
        "Calasiao" -> CalasiaoOrders(navController)
        else -> DefaultScreen()
    }
}

@Composable
fun DagupanOrders(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .width(360.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 20.dp, bottomStart = 5.dp, bottomEnd = 20.dp))
            )
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 20.dp, top = 12.dp)
            ) {

                Text(
                    text = "Jorose Po",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "5 items",
                    fontSize = 10.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            Row (modifier = Modifier.padding(start = 90.dp, top = 25.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("TB_OrdersActivity")
                    },
                    enabled = true
                ) {
                    Text(
                        text = "View order",
                        color = Color.Black,
                        fontFamily = poppinsFontFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View",
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun CalasiaoOrders(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .width(360.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.baskit_green),
                contentDescription = "baskit green",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 20.dp, bottomStart = 5.dp, bottomEnd = 20.dp))
            )
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 20.dp, top = 12.dp)
            ) {

                Text(
                    text = "Jorose Po",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "5 items",
                    fontSize = 10.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF737272)
                )
            }

            Row (modifier = Modifier.padding(start = 90.dp, top = 25.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("TB_OrdersActivity")
                    },
                    enabled = true
                ) {
                    Text(
                        text = "View order",
                        color = Color.Black,
                        fontFamily = poppinsFontFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View",
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun DefaultScreen() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.noorders_img),
            contentDescription = "No Orders",
            modifier = Modifier
                .size(205.dp)
        )

        Text(
            text = "No location selected yet.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFontFamily,
            color = Color (0xFF656565),
            modifier = Modifier.padding(10.dp)
        )
    }
}
