package pe.edu.upc.flota365.features.manager.data.remote.models

data class Report(
  val id: Int,
  val title: String,
  val description: String?,
  val createdAt: String?,
  val managerId: Int?,
  val fleetId: Int?,
  val vehicleId: Int?,
  val status: String?
)

data class CreateReportRequest(
  val title: String,
  val description: String?,
  val managerId: Int?,
  val fleetId: Int?,
  val vehicleId: Int?
)
