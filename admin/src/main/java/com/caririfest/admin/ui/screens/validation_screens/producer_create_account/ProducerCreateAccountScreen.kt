package com.caririfest.admin.ui.screens.validation_screens.producer_create_account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.caririfest.admin.model.AdminCreateRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProducerCreateAccountScreen(
    viewModel: ProducerCreateViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiSate.collectAsState()

    var adminName by remember { mutableStateOf("") }
    var adminLastName by remember { mutableStateOf("") }
    var docAdmin by remember { mutableStateOf("") }
    var adminEmail by remember { mutableStateOf("") }
    var adminEmailConfirm by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Crie sua conta") },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.Gray
                )
            )
        },
        floatingActionButton = {
            Button(
                onClick = {
                    viewModel.createAdmin(
                        request = AdminCreateRequest(
                            adminName,
                            adminLastName,
                            docAdmin,
                            adminEmail,
                            adminEmailConfirm,
                            password
                        )
                    )
                }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                } else {
                    Text(text = "Criar Conta")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = adminName,
                    onValueChange = { adminName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("Nome") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "name"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = adminLastName,
                    onValueChange = { adminLastName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("Sobrenome") }

                )

                Spacer(modifier = Modifier.height(26.dp))

                OutlinedTextField(
                    value = docAdmin,
                    onValueChange = { docAdmin = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("CPF / CNPJ") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "document"
                        )
                    }

                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = adminEmail,
                    onValueChange = { adminEmail = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("E-mail") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email"
                        )
                    }

                )

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = adminEmailConfirm,
                    onValueChange = { adminEmailConfirm = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("Confirme seu e-mail") }

                )

                Spacer(modifier = Modifier.height(26.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("Senha") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "password"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = "password"
                            )
                        }
                    },
                    visualTransformation = if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

            }

            uiState.isSuccess?.let {
                Text(
                    text = it,
                    color = Color.Green,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            uiState.isFailure?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}