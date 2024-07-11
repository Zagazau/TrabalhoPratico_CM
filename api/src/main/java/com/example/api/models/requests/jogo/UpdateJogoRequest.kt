package requests.jogo

import Jogador
import java.util.Date

data class UpdateJogoRequest(
    val torneio_id: String? = null,
    val data: Date? = null,
    val duracao: Date? = null,
    val arbitro: String? = null,
    val jogadores: List<Jogador>? = null
)
