package com.caririfest.admin.exceptions.authExceptions

sealed class AuthException(message: String): Exception(message) {
    class InvalidCredentials : AuthException("Email ou senha inválidos")
    class BlockedUser : AuthException("Usuário bloqueado")
    class Unknown : AuthException("Erro inesperado")
}