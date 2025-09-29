package com.caririfest.app_jnproject.ui.screens.news.details

import android.content.Intent
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.caririfest.app_jnproject.R
import com.caririfest.app_jnproject.font.montserratFamily
import com.caririfest.app_jnproject.shared.shareContent
import com.caririfest.app_jnproject.ui.components.BuyTicketButtonBar
import com.caririfest.app_jnproject.ui.components.ShareButton
import com.caririfest.app_jnproject.ui.components.Tag
import com.caririfest.network.model.Document
import com.caririfest.network.model.EventFields
import com.caririfest.network.model.FirestoreBoolean
import com.caririfest.network.model.FirestoreString
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
                },
                modifier = Modifier
                    .height(60.dp)
            )
        },
        bottomBar = {
            BuyTicketButtonBar(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, event.fields.link.stringValue.toUri())
                    context.startActivity(intent)
                },
                enable = event.fields.favorite.booleanValue
            )
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
                                appendLine("📍 Local: ${event.fields.location.stringValue}")
                                appendLine("📅 Data: ${event.fields.date.stringValue}")
                                appendLine()
                                appendLine("📲 Descubra mais eventos no Cariri com o app Cariri Fest!")
                                appendLine()
                                appendLine("👉 Baixe grátis: https://play.google.com/store/apps/details?id=seu.package")
                            }

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

            Column(modifier = Modifier.padding(top = 35.dp)) {
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

                Spacer(modifier = Modifier.height(16.dp))

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
                    Spacer(modifier = Modifier.width(4.dp))
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
                        painter = painterResource(R.drawable.ic_schedule),
                        contentDescription = stringResource(R.string.location),
                        tint = Color.DarkGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.fields.time.stringValue,
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
                        painter = painterResource(R.drawable.ic_personal_places),
                        contentDescription = stringResource(R.string.location),
                        tint = Color.Red,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.fields.place.stringValue,
                        style = typography.bodyMedium,
                        color = Color.Gray,
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
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.fields.location.stringValue,
                        style = typography.bodyMedium,
                        color = Color.Gray,
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Tag(
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

                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(6.dp))

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
            place = FirestoreString("Evento Presencial em cangaço bar"),
            time = FirestoreString("18:00"),
            img = FirestoreString("https://fakeimage.com/event.jpg"),
            favorite = FirestoreBoolean(false),
            link = FirestoreString("https://www.sympla.com.br/evento/gloria-tour-re-nascido-juazeiro-do-norte-25-10/3116330")
        )
    )


    NewsDetailsLayout(
        event = fakeEvent,
        navController = rememberNavController(),
    )
}