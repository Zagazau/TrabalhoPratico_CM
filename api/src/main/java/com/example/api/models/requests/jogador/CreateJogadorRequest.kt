package requests.jogador

data class CreateJogadorRequest(
    var id: String,
    var nome: String,
    var nacionalidade: String?
)