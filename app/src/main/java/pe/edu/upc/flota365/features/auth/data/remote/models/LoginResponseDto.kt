package pe.edu.upc.flota365.features.auth.data.remote.models

data class LoginResponseDto(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val fullName: String,
  val email: String,
  val role: String,
  val isActive: Boolean,
  val createdAt: String,
  val updatedAt: String
)
