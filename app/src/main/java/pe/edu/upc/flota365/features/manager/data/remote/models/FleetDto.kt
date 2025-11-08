package pe.edu.upc.flota365.features.manager.data.remote.models

data class FleetDto(
  val id: Int,
  val name: String,
  val description: String?,
  val vehicles: List<VehicleDto>?
)
