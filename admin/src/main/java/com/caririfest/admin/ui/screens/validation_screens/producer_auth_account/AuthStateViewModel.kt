package com.caririfest.admin.ui.screens.validation_screens.producer_auth_account

import androidx.lifecycle.ViewModel
import com.caririfest.admin.datastore.AuthPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthStateViewModel @Inject constructor(
    authPrefs: AuthPreferences
) : ViewModel() {
    val isLoggedIn = authPrefs.isLoggedIn
}

//Expõe Estado de Logado.