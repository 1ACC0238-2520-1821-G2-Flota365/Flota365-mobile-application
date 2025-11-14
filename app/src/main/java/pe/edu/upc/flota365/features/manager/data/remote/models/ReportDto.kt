package pe.edu.upc.flota365.features.manager.data.remote.models

data class Report(
  val id: String,
  val title: String,
  val description: String?,
  val type: String,
  val vehicleId: Int?,
  val vehicleName: String?,
  val createdBy: String?,
  val generatedAt: String,
  val status: String?
)

data class CreateReportRequest(
  val title: String,
  val type: String,
  val description: String,
  val generatedAt: String,
  val createdBy: String?
)
