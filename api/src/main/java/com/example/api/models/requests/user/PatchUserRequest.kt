package requests.user

data class PatchUserRequest(
    val email: String? = null,
    val name: String? = null,
    val address: String? = null,
    val avatar: String? = null
)