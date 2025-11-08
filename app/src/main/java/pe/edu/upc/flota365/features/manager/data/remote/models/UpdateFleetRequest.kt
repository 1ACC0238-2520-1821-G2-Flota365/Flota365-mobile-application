package pe.edu.upc.flota365.features.manager.data.remote.models

data class UpdateFleetRequest(
  val id: Int,
  val name: String,
  val description: String?
)
