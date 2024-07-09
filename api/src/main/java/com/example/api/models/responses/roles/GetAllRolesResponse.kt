package com.example.api.models.responses.roles

import com.example.api.models.Role

data class GetAllRolesResponse(
    val roles: List<com.example.api.models.Role>
)