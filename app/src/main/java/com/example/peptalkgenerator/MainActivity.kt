package com.example.peptalkgenerator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

//private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
            PepTalkGeneratorTheme {
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
    PepTalkScreen(  )
}

@Composable
fun PepTalkScreen(
    greetingViewModel: GreetingViewModel = viewModel()
){
    val phraseUIState by greetingViewModel.uiState.collectAsState()
    var currentGreeting = phraseUIState.currentGreeting
    //put it all together in a column
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .padding(4.dp)
            .fillMaxSize()
    ) {

        PepTalk(
            currentGreeting
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            greetingViewModel.getNewPepTalk()
            Modifier.fillMaxWidth()
        }) {
            Text(stringResource(R.string.generate_new),
                fontSize = 25.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            Image(imageVector = Icons.Default.Refresh,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        //region Share button
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"$currentGreeting")
            //$currentGreeting $randomFirst $randomSecond $randomSalutation"
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current

        Button(onClick = {context.startActivity(shareIntent)}){
            Text(stringResource(R.string.share_button),
                fontSize = 25.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            Image(imageVector = Icons.Default.Share,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White))
        }
        //endregion
    }
}

@Composable
fun PepTalk(
    currentGreeting: String
) {
    //region old setup
    //var randomGreeting by rememberSaveable {mutableStateOf("Click")}
    //var randomFirst by rememberSaveable {mutableStateOf("Generate New Pep Talk")}
    //var randomSecond by rememberSaveable {mutableStateOf("To")}
    //var randomSalutation by rememberSaveable {mutableStateOf("Generate New Pep Talk.")}

    //Get random greeting
    //val greeting: Array<String> = stringArrayResource(R.array.greetings_array)
    //val randomGreeting = Greeting()

    //Get random first part of pep talk
    //val first: Array<String> = stringArrayResource(R.array.first_part_array)

    //Get random second part
    //val second: Array<String> = stringArrayResource(R.array.second_part_array)

    //Get random salutation
    //val salutation: Array<String> = stringArrayResource(R.array.salutations_array)
    //endregion

    Text("$currentGreeting",
        //"$currentGreeting \n $randomFirst \n $randomSecond \n $randomSalutation",
        color = Color.White,
        fontSize = 35.sp,
        lineHeight = 40.sp
    )
}