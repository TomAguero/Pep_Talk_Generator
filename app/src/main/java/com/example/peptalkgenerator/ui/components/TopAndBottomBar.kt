package com.example.peptalkgenerator.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.peptalkgenerator.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peptalkgenerator.PepTalkViewModel

@Composable
fun TopAppBar(modifier: Modifier = Modifier){
    Text(
        stringResource(R.string.app_name),
        fontSize = 25.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BottomAppBar (
    pepTalkViewModel: PepTalkViewModel = viewModel(),
    pepTalk: String,
    modifier: Modifier = Modifier
){
    Row() {
        Button(onClick = {
            pepTalkViewModel.getNewPepTalk()
        }) {
            Text(
                stringResource(R.string.generate_new),
                fontSize = 25.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            Image(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        //region Share button
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, pepTalk)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current

        Button(onClick = { context.startActivity(shareIntent) }) {
            Text(
                stringResource(R.string.share_button),
                fontSize = 25.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            Image(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        //endregion
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview(){
    TopAppBar()
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview(){
    BottomAppBar(
        pepTalk = "Champ, the mere idea of you ha serious game, 24/7.",
        modifier = Modifier
    )
}