package com.caririfest.admin.ui.screens.validation_screens.producer_create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.admin.model.AdminCreateRequest
import com.caririfest.admin.model.AdminResponse
import com.caririfest.admin.repository.AdminsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProducerCreateViewModel @Inject constructor(
    private val adminsRepository: AdminsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiSate())
    val uiSate: StateFlow<UiSate> = _uiState


    fun createAdmin(request: AdminCreateRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = adminsRepository.createAdmin(request)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    adminResponse = response,
                    isSuccess = "Usuário Criado com Sucesso",
                    isFailure = null
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isFailure = e.message ?: "Erro inesperado",
                    isSuccess = null
                )
            }
        }
    }
}


data class UiSate(
    val isLoading: Boolean = false,
    val adminResponse: AdminResponse? = null,
    val isSuccess: String? = null,
    val isFailure: String? = null
)