package com.caririfest.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caririfest.admin.model.Event
import com.caririfest.admin.network.createEvent
import com.caririfest.admin.ui.theme.App_jnprojectTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            App_jnprojectTheme { HomeScreen() }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {

    val scroll = rememberScrollState()

    //date
    var inputDate by remember { mutableStateOf("") }
    //desc
    var inputDesc by remember { mutableStateOf("") }
    //favorite
    var inputFavorite by remember { mutableStateOf(false) }
    //hot
    var inputHot by remember { mutableStateOf(false) }
    //img
    var inputImg by remember { mutableStateOf("") }
    //link
    var inputLink by remember { mutableStateOf("") }
    //location
    var inputLocation by remember { mutableStateOf("") }
    //place
    var inputPlace by remember { mutableStateOf("") }
    //time
    var inputTime by remember { mutableStateOf("") }
    //title
    var inputTitle by remember { mutableStateOf("") }

    val newEvent = Event(
        date = inputDate,
        desc = inputDesc,
        favorite = inputFavorite,
        hot = inputHot,
        img = inputImg,
        link = inputLink,
        location = inputLocation,
        place = inputPlace,
        time = inputTime,
        title = inputTitle
    )

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
            .verticalScroll(scroll)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = inputTitle,
            onValueChange = { inputTitle = it },
            label = { Text(text = "Título do evento") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = inputTime,
            onValueChange = { inputTime = it },
            label = { Text(text = "Hora do evento") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = inputPlace,
            onValueChange = { inputPlace = it },
            label = { Text(text = "Local do evento") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = inputLocation,
            onValueChange = { inputLocation = it },
            label = { Text(text = "Cidade do evento") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = inputLink,
            onValueChange = { inputLink = it },
            label = { Text(text = "Link para compra do ingresso") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = inputImg,
            onValueChange = { inputImg = it },
            label = { Text(text = "Link banner do evento") }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Switch(
            checked = inputFavorite,
            onCheckedChange = { inputFavorite = it }
        )
        Switch(
            checked = inputHot,
            onCheckedChange = { inputHot = it }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = inputDesc,
            onValueChange = { inputDesc = it },
            label = { Text(text = "Descrição do evento") }
        )
        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = inputDate,
            onValueChange = { inputDate = it },
            label = { Text(text = "Data do evento") }
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = { createEvent(newEvent) }
        ) {
            Text("Crie o evento")
        }

    }

}
