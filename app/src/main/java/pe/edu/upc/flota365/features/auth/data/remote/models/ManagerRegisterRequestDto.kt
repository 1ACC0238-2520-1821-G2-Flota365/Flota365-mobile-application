package pe.edu.upc.flota365.features.auth.data.remote.models

data class ManagerRegisterRequestDto(
  val firstName: String,
  val lastName: String,
  val ruc: String,
  val businessName: String,
  val email: String,
  val phone: String,
  val password: String
)
