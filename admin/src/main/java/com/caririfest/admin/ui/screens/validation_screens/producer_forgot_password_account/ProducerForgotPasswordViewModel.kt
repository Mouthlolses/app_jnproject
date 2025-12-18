package com.caririfest.admin.ui.screens.validation_screens.producer_forgot_password_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.admin.model.recovery.ForgotPasswordRequest
import com.caririfest.admin.model.recovery.ResetPasswordRequest
import com.caririfest.admin.repository.AdminsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProducerForgotPasswordViewModel @Inject constructor(
    private val repository: AdminsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiSate())
    val uiState: StateFlow<UiSate> = _uiState.asStateFlow()


    fun recoverPassword(request: ForgotPasswordRequest) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            try {
                val response = repository.forgotPassword(request)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = response.message,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Erro inesperado",
                        successMessage = null
                    )
                }
            }
        }
    }

    fun resetPassword(request: ResetPasswordRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val response = repository.resetPassword(request)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = response.message,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Erro inesperado",
                        successMessage = null
                    )
                }
            }
        }
    }


    fun clearMessage() {
        _uiState.update {
            it.copy(
                successMessage = null, errorMessage = null
            )
        }
    }

}

data class UiSate(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)