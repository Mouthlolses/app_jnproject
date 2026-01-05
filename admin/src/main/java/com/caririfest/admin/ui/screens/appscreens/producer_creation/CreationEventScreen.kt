package com.caririfest.admin.ui.screens.appscreens.producer_creation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.caririfest.admin.ui.components.DockedDatePicker
import com.caririfest.admin.ui.components.DockedTimePicker
import com.caririfest.admin.ui.components.InfoRow
import com.caririfest.admin.ui.components.PublicEventFab
import com.caririfest.admin.ui.components.SubjectDropDown
import com.caririfest.admin.ui.components.SubjectDropDownOptional


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateEventScreen() {

    var eventName by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    var checkedBox by remember { mutableStateOf(false) }

    var addressName by remember { mutableStateOf("") }
    var localName by remember { mutableStateOf("") }
    var cepLocal by remember { mutableStateOf("") }
    var streetLocal by remember { mutableStateOf("") }
    var numberLocal by remember { mutableStateOf("") }
    var complementLocal by remember { mutableStateOf("") }
    var neighborLocal by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var stateLocal by remember { mutableStateOf("") }
    var eventDesc by remember { mutableStateOf("") }

    // Launcher do novo Photo Picker (Android 13+ e fallback para versões antigas)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImage = uri
    }

    val allFilled = listOf(
        eventName,
        addressName,
        localName,
        cepLocal,
        stateLocal,
        numberLocal,
        complementLocal,
        neighborLocal,
        city,
        stateLocal
    ).all { it.isNotBlank() }

    val data = allFilled && !checkedBox

    val subscriptionStatus = listOf("STANDARD", "PREMIUM")


    Scaffold(
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                ),
                colors = CardDefaults.cardColors(
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    16.dp
                )
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Criar Evento",
                        modifier = Modifier.padding(start = 12.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.padding(end = 12.dp),
                    ) {
                        Text("STANDARD")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

            }
        },
        floatingActionButton = {
            PublicEventFab(
                onClick = { },
                enabled = data
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        16.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(
                            label = "1. Informações básicas",
                            value = "Adicione as principais informações do evento"
                        )
                        OutlinedTextField(
                            value = eventName,
                            onValueChange = { eventName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            label = { Text("Nome do evento") }
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(text = "Imagem de divulgação (opcional)")

                        Spacer(modifier = Modifier.height(12.dp))

                        // Componente clicável para selecionar imagem
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(180.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.LightGray.copy(alpha = 0.3f))
                                    .clickable {
                                        launcher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (selectedImage == null) {
                                    Text(text = "Selecione uma imagem")
                                } else {
                                    AsyncImage(
                                        model = selectedImage,
                                        contentDescription = "imagem selecionada",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                            Text(
                                text = "A dimensão recomendada é de 1600 x 838\n" +
                                        "(mesma proporção do formato utilizado nas páginas de evento no Facebook).\n" +
                                        "Formato JPEG, GIF ou PNG de no máximo 2MB.\n" +
                                        "Imagens com dimensões diferentes serão redimensionadas."
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(text = "Classifique seu evento")
                        Spacer(modifier = Modifier.height(12.dp))
                        SubjectDropDown()
                        Spacer(modifier = Modifier.height(12.dp))
                        SubjectDropDownOptional()
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        16.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(
                            label = "2. Data e horário",
                            value = "Quando seu evento vai acontecer?"
                        )

                        DockedDatePicker(label = { Text(text = "Data de Início *") })
                        Spacer(modifier = Modifier.height(16.dp))
                        DockedTimePicker(label = { Text(text = "Hora de Início *") })
                        Spacer(modifier = Modifier.height(46.dp))
                        DockedDatePicker(label = { Text(text = "Data de Término  *") })
                        Spacer(modifier = Modifier.height(16.dp))
                        DockedTimePicker(label = { Text(text = "Hora de Término  *") })
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Seu evento vai durar ")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        16.dp
                    )
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InfoRow(
                            label = "3. Local e endereço",
                            value = "Local onde seu evento vai acontecer"
                        )

                        OutlinedTextField(
                            value = addressName,
                            onValueChange = { addressName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            label = { Text("Informe o endereço ou o nome do local do evento *") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (addressName.isNotBlank()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = cepLocal,
                                onValueChange = { cepLocal = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = { Text("CEP") },
                                maxLines = 8
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = streetLocal,
                                onValueChange = { streetLocal = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = { Text("Av./Rua") },
                                maxLines = 8
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = numberLocal,
                                onValueChange = { numberLocal = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = { Text("Número") },
                                maxLines = 8
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = complementLocal,
                                onValueChange = { complementLocal = it },
                                label = { Text("Complemento") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = neighborLocal,
                                onValueChange = { neighborLocal = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = { Text("Bairro") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = city,
                                onValueChange = { city = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = { Text("Cidade") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = stateLocal,
                                onValueChange = { stateLocal = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = { Text("Estado *") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        16.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            InfoRow(
                                label = "4. Ingressos",
                                value = "Que tipo de ingresso você deseja criar?"
                            )

                            OutlinedButton(
                                onClick = { }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                Text("INGRESSO PAGO")
                            }
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            Text(text = "Ou")
                            Spacer(modifier = Modifier.padding(vertical = 8.dp))
                            OutlinedButton(
                                onClick = { }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                                Text("INGRESSO GRATUITO")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        16.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InfoRow(
                            label = "5. Descrição do evento",
                            value = "Conte todos os detalhes do seu evento"
                        )

                        TextField(
                            value = eventDesc,
                            onValueChange = { eventDesc = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            label = { Text("Descrição do evento") },
                            placeholder = { Text("Ex: Programação e os diferenciais") },
                            maxLines = 150
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        16.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InfoRow(
                            label = "7. Termos e Conformidade",
                            value = "Compromisso do Organizador"
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checkedBox,
                                onCheckedChange = { checkedBox = it },
                            )
                            Text(
                                text = "Ao publicar este evento, estou de acordo com os Termos de uso , " +
                                        "com as Diretrizes de Comunidade e com as Regras de meia-entrada, " +
                                        "bem como declaro estar ciente da Política de Privacidade e das Obrigatoriedades Legais."
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            if (checkedBox) "Checkbox is checked" else "Checkbox is unchecked"
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


