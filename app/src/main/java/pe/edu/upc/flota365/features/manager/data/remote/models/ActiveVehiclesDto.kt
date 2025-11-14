package pe.edu.upc.flota365.features.manager.data.remote.models

data class ActiveVehicleDto(
  val id: Int,
  val licensePlate: String,
  val brand: String?,
  val model: String?,
  val year: Int?,
  val mileage: Int?,
  val status: Int?,
  val statusName: String?,
  val fleetId: Int?,
  val fleetName: String?,
  val driverId: Int?,
  val driverName: String?,
  val lastServiceDate: String?,
  val nextServiceDate: String?,
  val createdAt: String?,
  val updatedAt: String?
)
