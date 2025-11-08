package pe.edu.upc.flota365.features.manager.data.remote.models

data class VehicleDto(
  val id: Int,
  val licensePlate: String,   // ej. "ABC-123"
  val make: String?,          // ej. "Toyota"
  val model: String?,         // ej. "Hilux"
  val year: Int?,
  val vin: String?,           // número de serie (opcional)
  val mileage: Long?,         // km recorridos
  val status: String?,        // ej. "Activo", "En mantenimiento", "En ruta"
  val fleetId: Int?,          // relación con flota
  val lastMaintenanceDate: String?, // ISO date "2025-11-03T..."
  val currentLatitude: Double?,     // opcional para monitoreo
  val currentLongitude: Double?     // opcional para monitoreo
)
