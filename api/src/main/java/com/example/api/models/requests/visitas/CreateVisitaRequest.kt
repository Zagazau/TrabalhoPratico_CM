package requests.visitas

import java.util.Date

data class CreateVisitaRequest(
    val user_id: String,
    val torneio_id: String,
    val data_visita: Date,
    val hora_visita: Date
)