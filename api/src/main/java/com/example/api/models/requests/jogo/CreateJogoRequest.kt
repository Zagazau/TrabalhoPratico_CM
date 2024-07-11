package requests.jogo

import Jogador

data class CreateJogoRequest(
    val torneio_id: String,
    val data: String,
    val duracao: String,
    val arbitro: String,
    val jogadores: List<String>
)

