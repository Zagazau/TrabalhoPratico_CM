package requests.torneio

data class CreateTorneioRequest(
    val nome: String,
    val piso_id: String,
    val tipo_torneio_id: String,
    val local: String,
    val data_inicio: String,
    val data_fim: String
)