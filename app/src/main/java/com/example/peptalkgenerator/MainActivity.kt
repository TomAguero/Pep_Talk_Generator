package com.example.peptalkgenerator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        PepTalkGeneratorTheme {
            // A surface container using the 'background' color from the theme
            Surface{
                PepTalkApp()
            }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PepTalkApp(modifier: Modifier = Modifier) {
    GetPepTalk(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}


@Composable
fun GetPepTalk(modifier: Modifier = Modifier) {
    var randomGreeting by remember {mutableStateOf("Click")}
    var randomFirst by remember {mutableStateOf("Generate New Pep Talk")}
    var randomSecond by remember {mutableStateOf("To")}
    var randomSalutation by remember {mutableStateOf("Generate New Pep Talk.")}

    //region region get random strings
    //Get random greeting
    val greeting: Array<String> = stringArrayResource(R.array.greetings_array)
    //val randomGreeting = greeting.random()

    //Get random first part of pep talk
    val first: Array<String> = stringArrayResource(R.array.first_part_array)
    //val randomFirst = first.random()

    //Get random second part
    val second: Array<String> = stringArrayResource(R.array.second_part_array)
    //val randomSecond = second.random()

    //Get random salutation
    val salutation: Array<String> = stringArrayResource(R.array.salutations_array)
    //val randomSalutation = salutation.random()
    //endregion

    //put it all together in a column
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .padding(4.dp)
            .fillMaxSize()
    ) {

        Text(
            randomGreeting,
            color = Color.White,
            fontSize = 35.sp,
            lineHeight = 10.sp
        )
        Text(
            randomFirst,
            color = Color.White,
            fontSize = 35.sp,
            lineHeight = 10.sp
        )
        Text(
            randomSecond,
            color = Color.White,
            fontSize = 35.sp,
            lineHeight = 40.sp
        )
        Text(
            randomSalutation,
            color = Color.White,
            fontSize = 35.sp,
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            randomGreeting = greeting.random()
            randomFirst = first.random()
            randomSecond = second.random()
            randomSalutation = salutation.random()
        }) {
            Text(stringResource(R.string.generate_new),
                fontSize = 30.sp,
                lineHeight = 35.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT,"$randomGreeting $randomFirst $randomSecond $randomSalutation")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current

        Button(onClick = {
            context.startActivity(shareIntent)
        }){
            Text(stringResource(R.string.share_button),
                fontSize = 25.sp,
                lineHeight = 30.sp)
        }
    }
}