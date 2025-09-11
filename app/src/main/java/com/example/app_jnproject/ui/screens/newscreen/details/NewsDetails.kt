package com.example.app_jnproject.ui.screens.newscreen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.network.model.Document
import com.example.network.model.EventFields
import com.example.network.model.FirestoreBoolean
import com.example.network.model.FirestoreString
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState


@Composable
fun NewsDetailsLayout(
    event: Document
) {

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color.White)
    ) {
        MapboxMap(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .height(300.dp),
            mapViewportState = rememberMapViewportState {
                setCameraOptions {
                    zoom(2.0)
                    center(Point.fromLngLat(-98.0, 39.5))
                    pitch(0.0)
                    bearing(0.0)
//                    zoom(12.0)
//                    center(Point.fromLngLat(-39.3156, -7.2139))
//                    pitch(0.0)
//                    bearing(0.0)
                }
            }
        )
    }

    // DIVISOR
    Column(
        modifier = Modifier.padding(top = 400.dp)
    ) {
        HorizontalDivider(
            Modifier
                .padding(horizontal = 16.dp)
                .height(6.dp),
            color = DividerDefaults.color
        )

        // TÍTULO DO EVENTO

        Text(
            text = event.fields.title.stringValue,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )



        Text(
            text = event.fields.desc.stringValue,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )


        // LOCAL

        Text(
            text = "📍 Local: ${event.fields.location.stringValue}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )


        // DATA

        Text(
            text = "📅 Data: ${event.fields.date.stringValue}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}


@Preview
@Composable
fun NewsDetailsLayoutPreview() {
    val fakeEvent = Document(
        name = "projects/fakeProject/databases/(default)/documents/event/fakeId",
        fields = EventFields(
            title = FirestoreString("Festa do Cariri"),
            desc = FirestoreString("Um evento incrível com música e diversão!"),
            date = FirestoreString("10/09/2025"),
            location = FirestoreString("Praça Padre Cícero, Juazeiro do Norte"),
            img = FirestoreString("https://fakeimage.com/event.jpg"),
            favorite = FirestoreBoolean(false)
        )
    )

    NewsDetailsLayout(
        event = fakeEvent
    )
}