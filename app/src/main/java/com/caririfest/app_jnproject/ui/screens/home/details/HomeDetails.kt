package com.caririfest.app_jnproject.ui.screens.home.details

import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.caririfest.app_jnproject.R
import com.caririfest.app_jnproject.ui.screens.home.CityLocation

@Composable
fun DetailsScreen(
    city: CityLocation,
) {

    var progress by remember { mutableIntStateOf(0) }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var shouldReload by remember { mutableStateOf(false) }

    val url = city.url

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (hasError) {
            // Tela de erro personalizada
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.mascot_sf),
                        contentDescription = "mascot"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        errorMessage,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            hasError = false
                            progress = 0
                            shouldReload = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA500) // cor de destaque
                        )
                    ) {
                        Text(text = "Recarregar", color = Color.White)
                    }
                }
            }
        } else {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.cacheMode = WebSettings.LOAD_DEFAULT
                        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                hasError = false
                                progress = 0
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                // Se terminar e não tiver erro → progresso = 100%
                                if (!hasError) progress = 100
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                if (request?.isForMainFrame == true) {
                                    hasError = true
                                    errorMessage =
                                        "Não foi possível carregar a página.\nVerifique sua conexão."
                                }
                            }

                            override fun onReceivedHttpError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                errorResponse: WebResourceResponse?
                            ) {
                                if (request?.isForMainFrame == true) {
                                    hasError = true
                                    errorMessage =
                                        "Erro ao carregar a página: ${errorResponse?.statusCode}"
                                }
                            }

                            override fun onReceivedSslError(
                                view: WebView?,
                                handler: SslErrorHandler?,
                                error: SslError?
                            ) {
                                hasError = true
                                errorMessage = "Erro de certificado SSL"
                                handler?.cancel()
                            }
                        }


                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                super.onProgressChanged(view, newProgress)
                                progress = newProgress
                            }
                        }
                        loadUrl(url)
                    }
                },
                update = { webView ->
                    if (shouldReload) {
                        webView.loadUrl(url)
                        shouldReload = false
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (progress in 1..99) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


