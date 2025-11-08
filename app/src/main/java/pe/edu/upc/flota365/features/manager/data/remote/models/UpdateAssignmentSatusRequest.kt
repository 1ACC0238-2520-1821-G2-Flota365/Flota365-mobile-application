package pe.edu.upc.flota365.features.manager.data.remote.models

data class UpdateAssignmentStatusRequest(
  val id: Int,
  val status: String // Ej: "Started" o "Completed"
)
