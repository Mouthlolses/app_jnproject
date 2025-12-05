package com.caririfest.admin.ui.screens.validation_screens.producer_auth_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.admin.datastore.AuthPreferences
import com.caririfest.admin.model.login.LoginRequest
import com.caririfest.admin.model.login.LoginResponse
import com.caririfest.admin.repository.AdminsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProducerAuthViewModel @Inject constructor(
    private val repository: AdminsRepository,
    private val authPrefs: AuthPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiSate())
    val uiState: StateFlow<UiSate> = _uiState

    fun loginAdmin(request: LoginRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = repository.loginAdmin(request)

                // SE LOGIN FOI SUCCESS:
                authPrefs.setLoggedIn(true)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    adminResponse = response,
                    isSuccess = "Autenticação Realizada",
                    isFailure = "Autenticação Falhou"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = null,
                    isFailure = e.message ?: "Erro Inesperado",
                )
            }
        }
    }

}

data class UiSate(
    val isLoading: Boolean = false,
    val adminResponse: LoginResponse? = null,
    val isSuccess: String? = null,
    val isFailure: String? = null
)