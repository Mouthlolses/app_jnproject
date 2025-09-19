package com.example.app_jnproject.ui.screens.news.details

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.app_jnproject.R
import com.example.app_jnproject.font.montserratFamily
import com.example.app_jnproject.shared.shareContent
import com.example.app_jnproject.ui.components.ShareButton
import com.example.app_jnproject.ui.components.Tag
import com.example.network.model.Document
import com.example.network.model.EventFields
import com.example.network.model.FirestoreBoolean
import com.example.network.model.FirestoreString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsLayout(
    event: Document,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val loading: @Composable () -> Unit = { CircularProgressIndicator() }
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)
    val scrollState = rememberScrollState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(R.drawable.caririfestlogo1),
                        contentDescription = stringResource(R.string.logo),
                        modifier = Modifier.size(120.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back),
                            tint = Color(0xFFFF5733)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 26.dp,
                shadowElevation = 26.dp,
                color = Color.White,
                modifier = Modifier.drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = 0f + strokeWidth / 200
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            ) {
                Button(
                    onClick = { /* ação do botão */ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(36.dp),
                    colors = if (event.fields.favorite.booleanValue) {
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C853),
                            contentColor = Color.White
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9E9E9E),
                            contentColor = Color.White
                        )
                    }
                ) {
                    Text(
                        text = stringResource(R.string.buyTicket),
                        style = typography.bodyLarge,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
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

                            withContext(Dispatchers.Main) {
                                shareContent(context, bitmap, text)
                            }

                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 24.dp)
                )
            }

            Column(modifier = Modifier.padding(top = 40.dp)) {
                Text(
                    text = event.fields.title.stringValue,
                    style = typography.titleLarge,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.location),
                        tint = Color.DarkGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = event.fields.date.stringValue,
                        style = typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = stringResource(R.string.location),
                        tint = Color.Red,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = event.fields.location.stringValue,
                        style = typography.bodySmall,
                        color = Color.Gray,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Tag(
                            icon = R.drawable.ic_action_sell,
                            text =
                                if (event.fields.favorite.booleanValue)
                                    stringResource(R.string.available)
                                else
                                    stringResource(R.string.exhausted),
                            backgroundColor =
                                if (event.fields.favorite.booleanValue) Color(
                                    0xFF4CAF50
                                ) else Color(0xFF9E9E9E),
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = event.fields.desc.stringValue,
                    style = typography.bodyLarge,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
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
            desc = FirestoreString(
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
            ),
            date = FirestoreString("10/09/2025"),
            location = FirestoreString("Praça Padre Cícero, Juazeiro do Norte"),
            img = FirestoreString("https://fakeimage.com/event.jpg"),
            favorite = FirestoreBoolean(false)
        )
    )


    NewsDetailsLayout(
        event = fakeEvent,
        navController = rememberNavController(),
    )
}