package binar.ganda.challengechapter8.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import binar.ganda.challengechapter8.R
import binar.ganda.challengechapter8.model.ResponseUser
import binar.ganda.challengechapter8.ui.theme.ChallengeChapter8Theme
import binar.ganda.challengechapter8.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Register : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayRegister()
                }
            }
        }
    }
}

@Composable
private fun DisplayRegister() {

    var mContext = LocalContext.current
    val viewModelUser = viewModel(modelClass = UserViewModel::class.java)

    Column(
        modifier = Modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "Register",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        Spacer(modifier = Modifier.padding(20.dp))

        Image(
            painter = painterResource(id = R.drawable.movie_icon),
            contentDescription = "image_icon",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        val mContent = LocalContext.current
        var username by remember { mutableStateOf("") }
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter Username") },
        )

        var name by remember { mutableStateOf("") }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Name") },
        )

        var password by remember { mutableStateOf("") }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        var confirmpassword by remember { mutableStateOf("") }

        OutlinedTextField(
            value = confirmpassword,
            onValueChange = { confirmpassword = it },
            label = { Text("Enter Confirm password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.padding(30.dp))

        val mContext = LocalContext.current
        Button(
            onClick = {
                val registerUsername = username
                val registerName = name
                val registerPassword = password
                val registerConfirmPassword = confirmpassword

                if (registerUsername != "" && registerName != "" && registerPassword != "" && registerConfirmPassword != "") {
                    if (registerPassword == registerConfirmPassword) {
                        viewModelUser.insertNewUser(ResponseUser(
                            "",
                            "",
                            "http://placeimg.com/640/480",
                            registerName,
                            registerPassword,
                            "",
                            registerUsername
                        ))
                        mContext.startActivity(Intent(mContext, Login::class.java))
                    } else {
                        Toast.makeText(
                            mContext,
                            "Password dan Confirm Password Harus Sama",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(mContext, "Ada field yang kosong", Toast.LENGTH_SHORT).show()
                }

            }

                ,
            modifier = Modifier
                .width(150.dp)
        ) {
            Text(text = "Register")
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
fun DefaultPreview2() {
    ChallengeChapter8Theme {
        DisplayRegister()
    }
}