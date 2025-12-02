package com.caririfest.app.payment.stripe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.app.payment.stripe.data.remote.StripeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CheckoutViewModel @Inject constructor(
    private val repository: StripeRepository
) : ViewModel() {


    private val _clientSecret = MutableStateFlow<String?>(null)
    val clientSecret = _clientSecret.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    fun fetchPaymentIntent() {
        viewModelScope.launch {
            repository.getPaymentIntent()
                .onSuccess { result ->
                    _clientSecret.value = result.clientSecret
                }
                .onFailure { e ->
                    _error.value = e.localizedMessage
                }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
