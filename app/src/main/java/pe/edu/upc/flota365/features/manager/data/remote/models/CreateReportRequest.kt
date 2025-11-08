package pe.edu.upc.flota365.features.manager.data.remote.models

data class CreateReportRequest(
  val title: String,
  val description: String?,
  val managerId: Int?,
  val fleetId: Int?,
  val vehicleId: Int?
)
