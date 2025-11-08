package pe.edu.upc.flota365.features.manager.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import pe.edu.upc.flota365.features.manager.data.remote.models.*
import pe.edu.upc.flota365.features.manager.data.remote.services.ManagerService
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
  suspend fun createManager(request: CreateManagerApiRequest): Resource<Unit> =
    withContext(Dispatchers.IO) {
      try {
        val response = managerService.createManager(request)
        if (response.isSuccessful) {
          Resource.Success(Unit)
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }

  suspend fun getManagers(): Resource<List<Any>> =
    withContext(Dispatchers.IO) {
      try {
        val response = managerService.getManagers()
        if (response.isSuccessful) {
          Resource.Success(response.body() ?: emptyList())
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }

  // --- DASHBOARD ---
  suspend fun getDashboardStats(): Resource<Any> =
    safeApiCall { managerService.getDashboardStats() }

  suspend fun getActiveVehicles(): Resource<List<Any>> =
    safeApiCall { managerService.getActiveVehicles() }

  suspend fun getFleetSummary(): Resource<List<Any>> =
    safeApiCall { managerService.getFleetSummary() }

  // --- FLEETS ---
  suspend fun getFleets(): Resource<List<Any>> =
    safeApiCall { managerService.getFleets() }

  suspend fun createFleet(request: CreateFleetRequest): Resource<Unit> =
    safeApiCall { managerService.createFleet(request) }

  // --- VEHICLES ---
  suspend fun getVehicles(): Resource<List<Any>> =
    safeApiCall { managerService.getVehicles() }

  // --- DRIVERS ---
  suspend fun getDrivers(): Resource<List<Any>> =
    safeApiCall { managerService.getDrivers() }

  // --- REPORTS ---
  suspend fun getReports(): Resource<List<Any>> =
    safeApiCall { managerService.getReports() }

  suspend fun createReport(request: CreateReportRequest): Resource<Unit> =
    safeApiCall { managerService.createReport(request) }

  // --- HELPER GENÉRICO ---
  private suspend fun <T> safeApiCall(apiCall: suspend () -> retrofit2.Response<T>): Resource<T> =
    withContext(Dispatchers.IO) {
      try {
        val response = apiCall()
        if (response.isSuccessful) {
          Resource.Success(response.body()!!)
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }
}
