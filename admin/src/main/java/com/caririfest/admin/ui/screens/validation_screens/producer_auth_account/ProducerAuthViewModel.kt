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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProducerAuthViewModel @Inject constructor(
    private val repository: AdminsRepository,
    private val authPrefs: AuthPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiSate())
    val uiState: StateFlow<UiSate> = _uiState


    init {
        viewModelScope.launch {
            authPrefs.adminName.collect { name ->
                _uiState.value = _uiState.value.copy(
                    adminName = name ?: ""
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            authPrefs.adminEmail.collect { email ->
                _uiState.value = _uiState.value.copy(
                    adminEmail = email ?: ""
                )
            }
        }
    }

    fun loginAdmin(request: LoginRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = repository.loginAdmin(request)

                // SE LOGIN FOI SUCCESS:
                authPrefs.setLoggedIn(true)
                authPrefs.saveAdminName(response.admin.adminName)
                authPrefs.saveAdminEmail(response.admin.adminEmail)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    adminResponse = response,
                    isSuccess = "Autenticação Realizada",
                    isFailure = null
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

    fun logoutAdmin() {
        viewModelScope.launch {
            authPrefs.clearSession()

            _uiState.value = _uiState.value.copy(
                isSuccess = "Logout Realizado"
            )
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(isFailure = null, isSuccess = null) }
    }

}

data class UiSate(
    val isLoading: Boolean = false,
    val adminResponse: LoginResponse? = null,
    val adminName: String = "",
    val adminEmail: String = "",
    val isSuccess: String? = null,
    val isFailure: String? = null
)