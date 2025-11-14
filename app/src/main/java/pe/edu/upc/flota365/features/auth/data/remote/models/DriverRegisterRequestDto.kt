package pe.edu.upc.flota365.features.auth.data.remote.models
import kotlinx.serialization.Serializable


@Serializable
data class DriverRegisterRequestDto(
  val firstName: String,
  val lastName: String,
  val dni: String,
  val license: String,
  val email: String,
  val phone: String,
  val password: String
)
