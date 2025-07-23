
@Serializable
data class RegisterUser(
    val email: String,
    val password: String,
    val username: String
)

@Serializable
data class LoginUser(
    val email: String,
    val password: String
)