package com.example.app_jnproject.ui.screens.news.details

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.app_jnproject.shared.shareContent
import com.example.app_jnproject.ui.components.ShareButton
import com.example.network.model.Document
import com.example.network.model.EventFields
import com.example.network.model.FirestoreBoolean
import com.example.network.model.FirestoreString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun NewsDetailsLayout(
    event: Document
) {
    val scope = rememberCoroutineScope()
    val loading: @Composable () -> Unit = { CircularProgressIndicator() }
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Imagem com botão sobreposto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = event.fields.img.stringValue,
                contentDescription = "image_screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )

            // Botão sobreposto no final da imagem
            ShareButton(
                onClick = {
                    scope.launch {
                        val text = buildString {
                            appendLine("🎉 ${event.fields.title.stringValue}")
                            appendLine("📝 ${event.fields.desc.stringValue}")
                            appendLine("📍 Local: ${event.fields.location.stringValue}")
                            appendLine("📅 Data: ${event.fields.date.stringValue}")
                            appendLine()
                            appendLine("📲 Descubra mais eventos no Cariri com o app Cariri Fest!")
                            appendLine("👉 Baixe grátis: https://play.google.com/store/apps/details?id=seu.package")
                        }

                        //carrega a imagem via Coil
                        val request = ImageRequest.Builder(context)
                            .data(event.fields.img.stringValue)
                            .allowHardware(false)
                            .build()

                        val result = withContext(Dispatchers.IO) {
                            loading
                            imageLoader.execute(request)
                        }

                        val drawable = result.drawable
                        val bitmap = (drawable as BitmapDrawable).bitmap

                        // chama função utilitária
                        withContext(Dispatchers.Main) {
                            shareContent(context, bitmap, text)
                        }

                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 24.dp) // faz ele "sair" um pouco da imagem
            )
        }

        Column(modifier = Modifier.padding(top = 40.dp)) {
            HorizontalDivider(
                Modifier
                    .padding(horizontal = 16.dp)
                    .height(6.dp),
                color = DividerDefaults.color
            )

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

            Text(
                text = "📍 Local: ${event.fields.location.stringValue}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Text(
                text = "📅 Data: ${event.fields.date.stringValue}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
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