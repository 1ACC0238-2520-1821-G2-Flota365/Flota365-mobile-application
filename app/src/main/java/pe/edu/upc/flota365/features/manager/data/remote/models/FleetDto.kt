package pe.edu.upc.flota365.features.manager.data.remote.models

data class FleetDto(
  val id: Int,
  val code: String?,
  val name: String,
  val description: String?,
  val type: Int?,                     // puede ser null en algunos registros
  val typeName: String?,
  val isActive: Boolean = false,      // valor por defecto para evitar null crashes
  val vehicleCount: Int = 0,
  val activeVehicles: Int = 0,
  val inMaintenanceVehicles: Int = 0,
  val performance: Double? = null,
  val performancePercentage: Double? = null,
  val statusText: String?,
  val vehicleUtilization: Double? = null,
  val createdAt: String? = null,
  val updatedAt: String? = null
)
