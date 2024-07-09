package requests.visitas

import java.util.Date

data class UpdateVisitaRequest(
    val user_id: String? = null,
    val torneio_id: String? = null,
    val data_visita: Date? = null,
    val hora_visita: Date? = null
)