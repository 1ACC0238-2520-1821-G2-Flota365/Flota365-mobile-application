package pe.edu.upc.flota365.features.manager.data.remote.models

data class CreateDriverDto(
  val name: String,
  val licenseNumber: String,
  val phone: String
)
