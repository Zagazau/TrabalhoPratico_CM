package com.example.api.models.responses.jogadores

data class CreateJogadorResponse(
    val id: String,
    val nome: String,
    val nacionalidade: String?
)