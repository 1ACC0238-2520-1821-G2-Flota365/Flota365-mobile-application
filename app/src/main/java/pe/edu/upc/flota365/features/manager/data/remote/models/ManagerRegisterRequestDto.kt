package pe.edu.upc.flota365.features.manager.data.remote.models

data class ManagerRegisterRequestDto(
  val firstName: String,
  val lastName: String,
  val email: String,
  val password: String,
  val role: String
)
