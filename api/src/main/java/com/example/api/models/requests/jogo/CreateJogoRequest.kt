package requests.jogo

import java.util.Date

data class CreateJogoRequest(
    val torneio_id: String,
    val data: Date,
    val duracao: Date,
    val arbitro: String,
    val jogadores: List<JogadorResult>? = null
)

data class JogadorResult(
    val jogador_id: String,
    val resultados: List<Result>
)

data class Result(
    val pontuacao: Int
)

