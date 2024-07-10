import com.example.api.models.Role

data class User(
    val id: String,
    val email: String,
    val role: Role,
    val password: String?,
    val name: String?,
    val address: String?,
    val avatar: String?
)