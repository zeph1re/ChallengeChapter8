package binar.ganda.challengechapter8.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import binar.ganda.challengechapter8.R
import binar.ganda.challengechapter8.datastore.UserManager
import binar.ganda.challengechapter8.view.ui.theme.ChallengeChapter8Theme
import binar.ganda.challengechapter8.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mContext = LocalContext.current
                    val userManager = UserManager(mContext)
                    userManager.boolean.asLiveData().observe(this) {
                        if (it == true) {
                            mContext.startActivity(Intent(mContext, Home::class.java))
                        }
                    }
                    DisplayLogin()
                }
            }
        }
    }
}

@Composable
fun DisplayLogin() {

    val mContext = LocalContext.current
    val viewModelUser = viewModel(modelClass = UserViewModel::class.java)
    val dataUser by viewModelUser.dataUserState.collectAsState()
    val userManager = UserManager(mContext)


    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.password_eye)
    } else {
        painterResource(id = R.drawable.password_eye_off)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Image(
                painter = painterResource(id = R.drawable.movie_icon),
                contentDescription = "image_icon",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.padding(10.dp))


            var username by remember {
                mutableStateOf("")
            }
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                maxLines = 1,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.padding(20.dp)
            )

            var password by rememberSaveable {
                mutableStateOf("")
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility Icon"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.padding(20.dp))


            Button(
                onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        for (i in dataUser.indices) {
                            if (username == dataUser[i].username && password == dataUser[i].password) {
                                GlobalScope.launch {
                                    userManager.setBoolean(true)
                                    userManager.saveData(
                                        dataUser[i].alamat,
                                        dataUser[i].email,
                                        dataUser[i].name,
                                        dataUser[i].password,
                                        dataUser[i].tanggal_lahir,
                                        dataUser[i].username
                                    )
                                }
                                Toast.makeText(mContext, "Login berhasil", Toast.LENGTH_SHORT)
                                    .show()
                                mContext.startActivity(Intent(mContext, Home::class.java))
                            } else {
                                Toast.makeText(mContext, "Login Gagal", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            mContext,
                            "Ada Field yang Kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Text(text = "Belum Punya Akun?",
                fontSize = 12.sp,
                modifier = Modifier
                    .clickable {
                        mContext.startActivity(Intent(mContext, Register::class.java))
                    }
                    .padding(bottom = 60.dp))
        }
    }
}


@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview5() {
    ChallengeChapter8Theme {
        DisplayLogin()
    }
}