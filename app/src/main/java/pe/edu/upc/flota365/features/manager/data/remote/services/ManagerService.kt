package pe.edu.upc.flota365.features.manager.data.remote.services

import pe.edu.upc.flota365.features.manager.data.remote.models.ActiveVehicleDto
import pe.edu.upc.flota365.features.manager.data.remote.models.Assignment
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateAssignmentRequest
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateDriverDto
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateFleetRequest
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateMaintenanceRecordRequest
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateReportRequest
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateVehicleDto
import pe.edu.upc.flota365.features.manager.data.remote.models.DashboardStatsDto
import pe.edu.upc.flota365.features.manager.data.remote.models.DriverDto
import pe.edu.upc.flota365.features.manager.data.remote.models.DriverStatsDto
import pe.edu.upc.flota365.features.manager.data.remote.models.FleetDto
import pe.edu.upc.flota365.features.manager.data.remote.models.FleetSummaryDto
import pe.edu.upc.flota365.features.manager.data.remote.models.MaintenanceRecordDto
import pe.edu.upc.flota365.features.manager.data.remote.models.ManagerDto
import pe.edu.upc.flota365.features.manager.data.remote.models.Report
import pe.edu.upc.flota365.features.manager.data.remote.models.UpdateDriverDto
import pe.edu.upc.flota365.features.manager.data.remote.models.UpdateFleetRequest
import pe.edu.upc.flota365.features.manager.data.remote.models.UpdateVehicleDto
import pe.edu.upc.flota365.features.manager.data.remote.models.VehicleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManagerService {

  // --- MANAGER ---
  @GET("Manager")
  suspend fun getManagers(): Response<List<ManagerDto>>

  // --- DASHBOARD ---
  @GET("Dashboard/stats")
  suspend fun getDashboardStats(): Response<DashboardStatsDto>

  @GET("Dashboard/active-vehicles")
  suspend fun getActiveVehicles(): Response<List<ActiveVehicleDto>>

  @GET("Dashboard/fleet-summary")
  suspend fun getFleetSummary(): Response<List<FleetSummaryDto>>

  // --- FLEETS ---
  @GET("Fleets")
  suspend fun getFleets(): Response<List<FleetDto>>

  @POST("Fleets")
  suspend fun createFleet(@Body request: CreateFleetRequest): Response<Unit>

  @PUT("Fleets/{id}")
  suspend fun updateFleet(@Path("id") id: Int, @Body request: UpdateFleetRequest): Response<Unit>

  @DELETE("Fleets/{id}")
  suspend fun deleteFleet(@Path("id") id: Int): Response<Unit>

  // --- VEHICLES ---
  @GET("Vehicle")
  suspend fun getVehicles(): Response<List<VehicleDto>>

  @POST("Vehicle")
  suspend fun createVehicle(@Body request: CreateVehicleDto): Response<Unit>

  @PUT("Vehicle/{id}")
  suspend fun updateVehicle(@Path("id") id: Int, @Body request: UpdateVehicleDto): Response<Unit>

  @DELETE("Vehicle/{id}")
  suspend fun deleteVehicle(@Path("id") id: Int): Response<Unit>

  // --- DRIVERS ---
  @GET("Driver")
  suspend fun getDrivers(): Response<List<DriverDto>>

  @POST("Driver")
  suspend fun createDriver(@Body request: CreateDriverDto): Response<Unit>

  @PUT("Driver/{id}")
  suspend fun updateDriver(@Path("id") id: Int, @Body request: UpdateDriverDto): Response<Unit>

  @DELETE("Driver/{id}")
  suspend fun deleteDriver(@Path("id") id: Int): Response<Unit>

  @GET("Driver/stats")
  suspend fun getDriverStats(): Response<DriverStatsDto>

  // --- ASSIGNMENTS ---
  @GET("Assignment")
  suspend fun getAssignments(): Response<List<Assignment>>

  @POST("Assignment")
  suspend fun createAssignment(@Body request: CreateAssignmentRequest): Response<Unit>

  @PUT("Assignment/{id}/start")
  suspend fun startAssignment(@Path("id") id: Int): Response<Unit>

  @PUT("Assignment/{id}/complete")
  suspend fun completeAssignment(@Path("id") id: Int): Response<Unit>

  // --- MAINTENANCE ---
  @GET("Maintenance/records")
  suspend fun getMaintenanceRecords(): Response<List<MaintenanceRecordDto>>

  @POST("Maintenance/records")
  suspend fun createMaintenanceRecord(@Body request: CreateMaintenanceRecordRequest): Response<Unit>

  @GET("Maintenance/records/overdue")
  suspend fun getOverdueMaintenanceRecords(): Response<List<MaintenanceRecordDto>>

  // --- REPORTS ---
  @GET("Report")
  suspend fun getReports(): Response<List<Report>>

  @POST("Report")
  suspend fun createReport(@Body request: CreateReportRequest): Response<Unit>
}
