package pe.edu.upc.flota365.features.manager.data.remote.models

// === DTO que representa un conductor completo (GET /api/Driver) ===
data class DriverDto(
  val id: Int,
  val code: String,
  val firstName: String,
  val lastName: String,
  val fullName: String?,
  val licenseNumber: String,
  val licenseExpiryDate: String,
  val phone: String,
  val email: String,
  val experienceYears: Int,
  val status: Int,
  val statusName: String?,
  val assignedVehicle: String?,
  val isActive: Boolean,
  val createdAt: String?,
  val updatedAt: String?,
  val isLicenseExpiringSoon: Boolean
)

// === Crear nuevo conductor (POST /api/Driver) ===
data class CreateDriverDto(
  val code: String,
  val firstName: String,
  val lastName: String,
  val licenseNumber: String,
  val licenseExpiryDate: String,
  val phone: String,
  val email: String,
  val experienceYears: Int
)


// === Actualizar conductor (PUT /api/Driver/{id}) ===
data class UpdateDriverDto(
  val firstName: String,
  val lastName: String,
  val phone: String,
  val email: String,
  val licenseNumber: String,
  val licenseExpiryDate: String,
  val experienceYears: Int,
  val assignedVehicle: String?
)
// === Estadísticas de conductores (GET /api/Driver/stats) ===
data class DriverStatsDto(
  val totalDrivers: Int,
  val activeDrivers: Int,
  val inactiveDrivers: Int,
  val driversWithExpiredLicense: Int,
  val assignedDrivers: Int,
  val unassignedDrivers: Int,
  val averageExperience: Int
)
