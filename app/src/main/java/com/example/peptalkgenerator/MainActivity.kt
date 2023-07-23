package com.example.peptalkgenerator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.peptalkgenerator.ui.theme.PepTalkGeneratorTheme

class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        PepTalkGeneratorTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                GetPrepTalk()                    }
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }
}


@Composable
fun GetPrepTalk() {
    Column {
        val greeting: Array<String> = stringArrayResource(R.array.greetings_array)
        val randomGreeting = greeting.random()
        Text(randomGreeting)

        val first: Array<String> = stringArrayResource(R.array.first_part_array)
        val randomFirst = first.random()
        Text(randomFirst)

        val second: Array<String> = stringArrayResource(R.array.second_part_array)
        val randomSecond = second.random()
        Text(randomSecond)

        val salutation: Array<String> = stringArrayResource(R.array.salutations_array)
        val randomSalutation = salutation.random()
        Text(randomSalutation)
    }
}

@Preview(showBackground = true)
@Composable
fun PepTalkPreview() {
    PepTalkGeneratorTheme {
        GetPrepTalk()
    }
}