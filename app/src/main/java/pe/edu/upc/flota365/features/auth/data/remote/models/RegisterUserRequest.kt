package pe.edu.upc.flota365.features.auth.data.remote.models

data class RegisterUserRequest(
  val firstName: String,
  val lastName: String,
  val email: String,
  val password: String,
  val role: String
)
