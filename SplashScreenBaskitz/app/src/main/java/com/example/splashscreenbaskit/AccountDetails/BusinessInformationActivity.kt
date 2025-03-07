//package com.example.splashscreenbaskit.AccountDetails
//
//import android.widget.Toast
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Done
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.*
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.splashscreenbaskit.R
//import com.example.splashscreenbaskit.ui.theme.poppinsFontFamily
//
//@Preview
//@Composable
//fun BusinessInfoPreview (){
//   BusinessInformationActivity(navController = rememberNavController())
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BusinessInformationActivity(navController: NavController) {
//    var shopName by remember { mutableStateOf("") }
//    var shopAddress by remember { mutableStateOf("") }
//    var selectedStoreType by remember { mutableStateOf("Standard") }
//    val context = LocalContext.current
//
//    val isButtonEnabled = shopName.isNotBlank() && shopAddress.isNotBlank()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
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
//                    Icon(
//                        imageVector = Icons.Filled.Done,
//                        contentDescription = "Done",
//                        tint = Color.White
//                    )
//                }
//                Divider(
//                    color = Color(0xFF1D7151),
//                    modifier = Modifier
//                        .width(60.dp)
//                        .height(2.dp)
//                )
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color(0xFF1D7151), CircleShape),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "2",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        fontFamily = poppinsFontFamily
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(65.dp))
//
//            Text(
//                "Business Information",
//                fontWeight = FontWeight.Bold,
//                fontSize = 24.sp,
//                fontFamily = poppinsFontFamily
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 40.dp),
//                horizontalAlignment = Alignment.Start
//            ) {
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Text(text = "Registered shop name",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black)
//                OutlinedTextField(
//                    value = shopName,
//                    onValueChange = { shopName = it },
//                    placeholder = {
//                        Text("Enter your shop name",
//                            fontSize = 14.sp,
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
//                    text = "Registered shop address",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black)
//                OutlinedTextField(
//                    value = shopAddress,
//                    onValueChange = { shopAddress = it },
//                    placeholder = {
//                        Text("Enter your shop address",
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
//                Spacer(modifier = Modifier.height(15.dp))
//
//                Text(
//                    modifier = Modifier.padding(bottom = 5.dp),
//                    text = "Certificate of registration",
//                    fontSize = 14.sp,
//                    fontFamily = poppinsFontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black)
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(45.dp)
//                        .background(Color(0xFFECECEC), RoundedCornerShape(10.dp))
//                        .clickable {
//                            Toast
//                                .makeText(context, "Upload Certificate", Toast.LENGTH_SHORT)
//                                .show()
//                        },
//                    contentAlignment = Alignment.CenterStart
//                ) {
//                    Row(
//                        modifier = Modifier.padding(start = 15.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text("Upload image",
//                            fontSize = 14.sp,
//                            fontFamily = poppinsFontFamily,
//                            color = Color(0xFF8C8C8C)
//                        )
//                        Spacer(modifier = Modifier.width(170.dp))
//                        Image(
//                            painter = painterResource(id = R.drawable.upload),
//                            contentDescription = "Upload Icon",
//                            modifier = Modifier.size(14.dp)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//                Row(modifier = Modifier.fillMaxWidth()
//                ){
//                    Text(
//                        modifier = Modifier.padding(bottom = 5.dp),
//                        text = "Valid ID",
//                        fontSize = 14.sp,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Black
//                    )
//                    Spacer(modifier = Modifier.width(5.dp))
//                    Icon(
//                        imageVector = Icons.Default.Info,
//                        contentDescription = "Info",
//                        tint = Color(0xFF4557FF),
//                        modifier = Modifier.size(18.dp)
//                    )
//                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(45.dp)
//                        .background(Color(0xFFECECEC), RoundedCornerShape(10.dp))
//                        .clickable {
//                            Toast
//                                .makeText(context, "Upload Valid ID", Toast.LENGTH_SHORT)
//                                .show()
//                        },
//                    contentAlignment = Alignment.CenterStart
//                ) {
//                    Row(
//                        modifier = Modifier.padding(start = 15.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text("Upload image",
//                            fontSize = 14.sp,
//                            fontFamily = poppinsFontFamily,
//                            color = Color(0xFF8C8C8C)
//                        )
//                        Spacer(modifier = Modifier.width(170.dp))
//                        Image(
//                            painter = painterResource(id = R.drawable.upload),
//                            contentDescription = "Upload Icon",
//                            modifier = Modifier.size(14.dp)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(30.dp))
//
//                Column (horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(
//                        modifier = Modifier.padding(bottom = 5.dp),
//                        text = "Choose your store type",
//                        fontSize = 14.sp,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    )
//
//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Button(
//                            onClick = { selectedStoreType = "Standard" },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (selectedStoreType == "Standard") Color(0xFFE0F4DE) else Color(0xFFECECEC)
//                            ),
//                            shape = CircleShape,
//                            modifier = Modifier.wrapContentSize()
//                        ) {
//                            Text("Standard",
//                                fontSize = 14.sp,
//                                fontFamily = poppinsFontFamily,
//                                fontWeight = if (selectedStoreType == "Standard") FontWeight.SemiBold else FontWeight.Normal,
//                                color = Color.Black,
//
//                                )
//                        }
//
//                        Spacer(modifier = Modifier.width(30.dp))
//
//                        Button(
//                            onClick = { selectedStoreType = "Partnership" },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (selectedStoreType == "Partnership") Color(0xFFE0F4DE) else Color(0xFFECECEC)
//                            ),
//                            shape = CircleShape,
//                            modifier = Modifier.wrapContentSize()
//                        ) {
//                            Text("Partnership",
//                                fontSize = 14.sp,
//                                fontFamily = poppinsFontFamily,
//                                fontWeight = if (selectedStoreType == "Partnership") FontWeight.SemiBold else FontWeight.Normal,
//                                color = Color.Black
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Button(
//                        onClick = {navController.navigate("RequestSentScreen")},
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D7151)),
//                        modifier = Modifier.fillMaxWidth().height(50.dp),
//                        enabled = isButtonEnabled
//                    ) {
//                        Text(
//                            text = "Send request",
//                            fontSize = 14.sp,
//                            fontFamily = poppinsFontFamily,
//                            fontWeight = FontWeight.SemiBold,
//                            color = Color.White
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
