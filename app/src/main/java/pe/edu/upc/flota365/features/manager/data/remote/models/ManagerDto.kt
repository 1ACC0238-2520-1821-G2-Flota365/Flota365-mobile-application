package pe.edu.upc.flota365.features.manager.data.remote.models

data class ManagerDto(
  val id: Int,
  val name: String,
  val email: String,
  val fleets: List<FleetDto>?
)
