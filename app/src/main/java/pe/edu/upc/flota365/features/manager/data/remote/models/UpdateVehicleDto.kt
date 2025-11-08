package pe.edu.upc.flota365.features.manager.data.remote.models

data class UpdateVehicleDto(
  val id: Int,
  val licensePlate: String?,
  val make: String?,
  val model: String?,
  val year: Int?,
  val vin: String?,
  val mileage: Long?,
  val status: String?,
  val fleetId: Int?
)
