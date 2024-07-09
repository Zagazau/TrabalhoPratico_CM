package com.example.api.models.responses.jogadores

import Jogador

data class GetAllJogadoresResponse(
    val jogadores: List<Jogador>
)