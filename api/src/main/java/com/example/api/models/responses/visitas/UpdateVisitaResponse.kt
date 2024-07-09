package com.example.api.models.responses.visitas


data class UpdateVisitaResponse(
    val id: String,
    val user_id: String,
    val torneio_id: String,
    val data_visita: String,
    val hora_visita: String
)