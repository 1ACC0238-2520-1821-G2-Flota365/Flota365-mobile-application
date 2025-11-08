package pe.edu.upc.flota365.features.manager.data.remote.models

data class CreateAssignmentRequest(
  val vehicleId: Int,
  val driverId: Int,
  val description: String
)
