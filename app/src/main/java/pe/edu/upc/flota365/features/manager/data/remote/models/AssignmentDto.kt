package pe.edu.upc.flota365.features.manager.data.remote.models

data class Assignment(
  val id: Int,
  val vehicleId: Int,
  val driverId: Int,
  val routeDescription: String?,
  val startDate: String?,
  val endDate: String?,
  val status: String?,
  val createdAt: String?,
  val updatedAt: String?
)

data class CreateAssignmentRequest(
  val vehicleId: Int,
  val driverId: Int,
  val routeDescription: String?
)

data class UpdateAssignmentStatusRequest(
  val id: Int,
  val status: String
)
