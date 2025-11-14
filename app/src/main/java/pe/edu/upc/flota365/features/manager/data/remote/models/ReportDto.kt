package pe.edu.upc.flota365.features.manager.data.remote.models

/**
 * Representa un reporte generado o recuperado desde el backend.
 */
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

/**
 * Representa la solicitud para crear un nuevo reporte en el backend.
 */
data class CreateReportRequest(
  val title: String,
  val type: String,
  val description: String,
  val generatedAt: String,
  val createdBy: String?
)
