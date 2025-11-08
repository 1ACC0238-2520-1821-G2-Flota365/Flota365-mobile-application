package pe.edu.upc.flota365.features.manager.data.remote.models

data class Report(
  val id: String,
  val title: String,
  val type: String,
  val generatedAt: String,
  val createdBy: String? = null // ahora opcional
)
data class CreateReportRequest(
  val title: String,
  val type: String,
  val generatedAt: String,
  val createdBy: String?
)
