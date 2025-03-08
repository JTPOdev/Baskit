//package com.example.splashscreenbaskit.AccountDetails
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.*
//import com.example.splashscreenbaskit.R
//import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
//
//@Preview
//@Composable
//fun ShopInfoPreview (){
//    ShopInformationScreen(navController = rememberNavController())
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ShopInformationScreen(navController: NavController) {
//    var shopName by remember { mutableStateOf("") }
//    var shopAddress by remember { mutableStateOf("") }
//    var ownerName by remember { mutableStateOf("") }
//    var ownerNumber by remember { mutableStateOf("") }
//
//    val options = listOf("Dagupan", "Calasiao")
//    var expanded by remember { mutableStateOf(false) }
//    var selectedOption by remember { mutableStateOf(options[0]) }
//
//    val isButtonEnabled = shopName.isNotBlank() && shopAddress.isNotBlank() && ownerName.isNotBlank() && ownerNumber.isNotBlank()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//
//    ) {
//        Spacer(modifier = Modifier.height(50.dp))
//
//        Text(
//            text = "< Back",
//            fontSize = 15.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.SemiBold,
//            fontFamily = poppinsFontFamily,
//            modifier = Modifier
//                .padding(start = 30.dp)
//                .clickable { navController.popBackStack() }
//        )
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//        Column (
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color(0xFF1D7151), CircleShape),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "1",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = poppinsFontFamily
//                    )
//                }
//                Divider(
//                    color = Color(0xFFD9D9D9),
//                    modifier = Modifier
//                        .width(60.dp)
//                        .height(2.dp)
//                )
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color(0xFFA2A2A2), CircleShape),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "2",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = poppinsFontFamily)
//                }
//            }
//            Spacer(modifier = Modifier.height(65.dp))
//
//            Text(
//                "Shop Information",
//                fontWeight = FontWeight.Bold,
//                fontSize = 24.sp,
//                fontFamily = poppinsFontFamily
//            )
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 40.dp),
//                horizontalAlignment = Alignment.Start
//            ) {
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Text(
//                    text = "Store Name",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black)
//                OutlinedTextField(
//                    value = shopName,
//                    onValueChange = { shopName = it },
//                    placeholder = {
//                        Text("Enter your store name",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = poppinsFontFamily,
//                            color = Color(0xFF8C8C8C)
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true,
//                    shape = RoundedCornerShape(10.dp),
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = Color.Black,
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(
//                    text = "Store Address",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black)
//                OutlinedTextField(
//                    value = shopAddress,
//                    onValueChange = { shopAddress = it },
//                    placeholder = {
//                        Text("Enter your store address",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = poppinsFontFamily,
//                            color = Color(0xFF8C8C8C)
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp),
//                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = Color.Black,
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(
//                    text = "Store Location",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black
//                )
//                ExposedDropdownMenuBox(
//                    expanded = expanded,
//                    onExpandedChange = {expanded = !expanded}
//                ) {
//                    OutlinedTextField(
//                        modifier = Modifier.fillMaxWidth() .menuAnchor(),
//                        readOnly = true,
//                        value = selectedOption,
//                        onValueChange = {},
//                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
//                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = Color.Black,
//                            unfocusedBorderColor = Color.Gray
//                        ),
//                        shape = RoundedCornerShape(10.dp)
//                    )
//
//                    ExposedDropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = {expanded = false}
//                    ) {
//                        options.forEach{
//                            DropdownMenuItem(
//                                text = {Text(text = it,style = TextStyle(fontFamily = poppinsFontFamily))},
//                                onClick = {
//                                    selectedOption = it
//                                    expanded = false
//                                },
//                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(
//                    text = "Owner Name",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black
//                )
//                OutlinedTextField(
//                    value = ownerName,
//                    onValueChange = { ownerName = it },
//                    placeholder = {
//                        Text("Enter your name",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = poppinsFontFamily,
//                            color = Color(0xFF8C8C8C)
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp),
//                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = Color.Black,
//                        unfocusedBorderColor = Color.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Text(
//                    text = "Owner Number",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black)
//                OutlinedTextField(
//                    value = ownerNumber,
//                    onValueChange = { ownerNumber = it },
//                    placeholder = {
//                        Text("Enter your number",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = poppinsFontFamily,
//                            color = Color(0xFF8C8C8C)
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp),
//                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = Color.Black,
//                        unfocusedBorderColor = Color.Gray
//                    ),
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//
//                Spacer(modifier = Modifier.height(50.dp))
//
//                Button(
//                    onClick = {navController.navigate("BusinessInformationActivity")},
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
//                    modifier = Modifier.fillMaxWidth().height(50.dp),
//                    enabled = isButtonEnabled
//                ) {
//                    Text("Next", color = Color.White)
//                }
//            }
//        }
//    }
//}
