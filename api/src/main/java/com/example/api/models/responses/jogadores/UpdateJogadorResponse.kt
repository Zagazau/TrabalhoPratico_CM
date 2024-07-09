package com.example.api.models.responses.jogadores

data class UpdateJogadorResponse(
    val id: String,
    val nome: String,
    val nacionalidade: String?
)