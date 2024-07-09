package requests.auth

data class LoginRequest(
    var email: String,
    var password: String,
)