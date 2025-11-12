package pe.edu.upc.flota365.features.manager.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.flota365.core.network.RetrofitInstance.api
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import pe.edu.upc.flota365.features.manager.data.remote.models.*
import pe.edu.upc.flota365.features.manager.data.remote.services.ManagerService
import retrofit2.Response
import javax.inject.Inject

class ManagerRepositoryImpl @Inject constructor(
  private val authService: AuthService,
  private val managerService: ManagerService
) {

  // --- AUTH / REGISTRO ---
  suspend fun registerManagerAndUser(request: ManagerRegisterRequestDto): Resource<Unit> =
    withContext(Dispatchers.IO) {
      try {
        val response = authService.registerUser(request)
        if (response.isSuccessful) {
          Resource.Success(Unit)
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }

  // --- MANAGERS ---
  suspend fun getManagers(): Resource<List<ManagerDto>> =
    safeApiCall { managerService.getManagers() }

  // --- DASHBOARD ---
  suspend fun getDashboardStats(): Resource<DashboardStatsDto> =
    safeApiCall { managerService.getDashboardStats() }

  suspend fun getActiveVehicles(): Resource<List<ActiveVehicleDto>> =
    safeApiCall { managerService.getActiveVehicles() }

  suspend fun getFleetSummary(): Resource<FleetSummaryDto> {
    return safeApiCall { managerService.getFleetSummary() }
  }

  // --- FLEETS ---
  suspend fun getFleets(): Resource<List<FleetDto>> =
    safeApiCall { managerService.getFleets() }

  suspend fun createFleet(request: CreateFleetRequest): Resource<Unit> =
    safeApiCall { managerService.createFleet(request) }

  suspend fun updateFleet(id: Int, request: UpdateFleetRequest): Resource<Unit> =
    safeApiCall { managerService.updateFleet(id, request) }

  suspend fun deleteFleet(id: Int): Resource<Unit> =
    safeApiCall { managerService.deleteFleet(id) }

  // --- VEHICLES ---
  suspend fun getVehicles(): Resource<List<VehicleDto>> =
    safeApiCall { managerService.getVehicles() }

  suspend fun createVehicle(request: CreateVehicleDto): Resource<Unit> =
    safeApiCall { managerService.createVehicle(request) }

  suspend fun updateVehicle(id: Int, request: UpdateVehicleDto): Resource<Unit> =
    safeApiCall { managerService.updateVehicle(id, request) }

  suspend fun deleteVehicle(id: Int): Resource<Unit> =
    safeApiCall { managerService.deleteVehicle(id) }

  // --- DRIVERS ---
  suspend fun getDrivers(): Resource<List<DriverDto>> =
    safeApiCall { managerService.getDrivers() }

  suspend fun createDriver(request: CreateDriverDto): Resource<Unit> =
    safeApiCall { managerService.createDriver(request) }

  suspend fun updateDriver(id: Int, request: UpdateDriverDto): Resource<Unit> =
    safeApiCall { managerService.updateDriver(id, request) }

  suspend fun deleteDriver(id: Int): Resource<Unit> =
    safeApiCall { managerService.deleteDriver(id) }

  suspend fun getDriverStats(): Resource<DriverStatsDto> =
    safeApiCall { managerService.getDriverStats() }

  // --- ASSIGNMENTS ---
  suspend fun getAssignments(): Resource<List<Assignment>> =
    safeApiCall { managerService.getAssignments() }

  suspend fun createAssignment(request: CreateAssignmentRequest): Resource<Unit> =
    safeApiCall { managerService.createAssignment(request) }

  suspend fun startAssignment(id: Int): Resource<Unit> =
    safeApiCall { managerService.startAssignment(id) }

  suspend fun completeAssignment(id: Int): Resource<Unit> =
    safeApiCall { managerService.completeAssignment(id) }

  // --- MAINTENANCE ---
  suspend fun getMaintenanceRecords(): Resource<List<MaintenanceRecordDto>> =
    safeApiCall { managerService.getMaintenanceRecords() }

  suspend fun createMaintenanceRecord(request: CreateMaintenanceRecordRequest): Resource<Unit> =
    safeApiCall { managerService.createMaintenanceRecord(request) }

  suspend fun getOverdueMaintenanceRecords(): Resource<List<MaintenanceRecordDto>> =
    safeApiCall { managerService.getOverdueMaintenanceRecords() }

  // --- REPORTS ---
  suspend fun getReports(): Resource<List<Report>> =
    safeApiCall { managerService.getReports() }

  suspend fun createReport(request: CreateReportRequest): Resource<String> =
    withContext(Dispatchers.IO) {
      try {
        val response = managerService.postReport(request)
        if (response.isSuccessful) {
          Resource.Success("Reporte generado correctamente")
        } else {
          Resource.Error("Error al generar reporte: ${response.code()} ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }
  suspend fun getReportsFiltered(
    type: String? = null,
    vehicleId: Int? = null,
    fromDate: String? = null,
    toDate: String? = null
  ): Resource<List<Report>> = safeApiCall {
    managerService.getReportsFiltered(type, vehicleId, fromDate, toDate)
  }
  // --- MÉTODO GENÉRICO ---
  private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> =
    withContext(Dispatchers.IO) {
      try {
        val response = apiCall()
        if (response.isSuccessful) {
          val body = response.body()
          if (body != null) {
            Resource.Success(body)
          } else {
            @Suppress("UNCHECKED_CAST")
            Resource.Success(Unit as T) // para endpoints vacíos
          }
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }
}
