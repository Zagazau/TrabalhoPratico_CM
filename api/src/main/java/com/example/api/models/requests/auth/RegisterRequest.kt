package requests.auth

data class RegisterRequest(
    var name: String,
    var email: String,
    var password: String,
    var role_id: String?
)