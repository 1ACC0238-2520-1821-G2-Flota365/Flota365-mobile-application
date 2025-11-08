package pe.edu.upc.flota365.features.manager.presentation.event

import pe.edu.upc.flota365.features.manager.data.remote.models.*

sealed class ManagerUiEvent {

  // --- DASHBOARD ---
  object LoadDashboard : ManagerUiEvent()

  // --- FLEETS ---
  object LoadFleets : ManagerUiEvent()
  data class CreateFleet(val request: CreateFleetRequest) : ManagerUiEvent()
  data class UpdateFleet(val id: Int, val request: UpdateFleetRequest) : ManagerUiEvent()
  data class DeleteFleet(val id: Int) : ManagerUiEvent()

  // --- VEHICLES ---
  object LoadVehicles : ManagerUiEvent()
  data class CreateVehicle(val request: CreateVehicleDto) : ManagerUiEvent()
  data class UpdateVehicle(val id: Int, val request: UpdateVehicleDto) : ManagerUiEvent()
  data class DeleteVehicle(val id: Int) : ManagerUiEvent()

  // --- DRIVERS ---
  object LoadDrivers : ManagerUiEvent()
  data class CreateDriver(val request: CreateDriverDto) : ManagerUiEvent()
  data class UpdateDriver(val id: Int, val request: UpdateDriverDto) : ManagerUiEvent()
  data class DeleteDriver(val id: Int) : ManagerUiEvent()

  // --- ASSIGNMENTS ---
  object LoadAssignments : ManagerUiEvent()
  data class CreateAssignment(val request: CreateAssignmentRequest) : ManagerUiEvent()
  data class StartAssignment(val id: Int) : ManagerUiEvent()
  data class CompleteAssignment(val id: Int) : ManagerUiEvent()

  // --- MAINTENANCE ---
  object LoadMaintenance : ManagerUiEvent()
  data class CreateMaintenanceRecord(val request: CreateMaintenanceRecordRequest) : ManagerUiEvent()

  // --- REPORTS ---
  object LoadReports : ManagerUiEvent()
  data class CreateReport(val request: CreateReportRequest) : ManagerUiEvent()
}
