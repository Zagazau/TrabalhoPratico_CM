package com.example.api.models.responses.users

import User

data class GetAllUsersResponse(
    val users: List<User>
)