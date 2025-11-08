package pe.edu.upc.flota365.features.manager.data.remote.models

data class UpdateDriverDto(
  val id: Int,
  val name: String,
  val licenseNumber: String,
  val phone: String
)
