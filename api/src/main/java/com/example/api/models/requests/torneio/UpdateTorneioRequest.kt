package requests.torneio

data class UpdateTorneioRequest(
    val nome: String? = null,
    val piso_id: String? = null,
    val tipo_torneio_id: String? = null,
    val local: String? = null,
    val data_inicio: String? = null,
    val data_fim: String? = null
)