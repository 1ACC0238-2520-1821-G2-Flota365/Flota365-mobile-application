package pe.edu.upc.flota365.data.remote.dto

import com.squareup.moshi.Json
import pe.edu.upc.flota365.domain.model.Vehicle

data class VehicleDto(
  @Json(name = "id") val id: Int,
  @Json(name = "plateNumber") val plateNumber: String,
  @Json(name = "model") val model: String,
  @Json(name = "brand") val brand: String,
  @Json(name = "year") val year: Int,
  @Json(name = "status") val status: String
) {
  fun toDomain(): Vehicle {
    return Vehicle(
      id = id,
      licensePlate = plateNumber, // <-- coincide con Vehicle.kt
      brand = brand,
      model = model,
      year = year,
      status = status
    )
  }
}
