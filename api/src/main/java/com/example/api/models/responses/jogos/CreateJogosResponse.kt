package com.example.api.models.responses.jogos

data class CreateJogosResponse(
    val id: String,
    val torneio_id: String,
    val data: String,
    val duracao: String,
    val arbitro: String
)