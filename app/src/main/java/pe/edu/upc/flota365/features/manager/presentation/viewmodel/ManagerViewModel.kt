package pe.edu.upc.flota365.features.manager.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import pe.edu.upc.flota365.features.manager.presentation.event.ManagerUiEvent
import pe.edu.upc.flota365.features.manager.presentation.state.ManagerUiState
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  var uiState by mutableStateOf(ManagerUiState())
    private set

  fun onEvent(event: ManagerUiEvent) {
    viewModelScope.launch {
      when (event) {

        // --- DASHBOARD ---
        is ManagerUiEvent.LoadDashboard -> {
          handleResource(repository.getDashboardStats()) {
            uiState = uiState.copy(dashboardStats = it)
          }
          handleResource(repository.getActiveVehicles()) {
            uiState = uiState.copy(activeVehicles = it)
          }
          handleResource(repository.getFleetSummary()) {
            uiState = uiState.copy(fleetSummary = it)
          }
        }

        // --- FLEETS ---
        is ManagerUiEvent.LoadFleets ->
          handleResource(repository.getFleets()) {
            uiState = uiState.copy(fleets = it)
          }

        is ManagerUiEvent.CreateFleet -> {
          repository.createFleet(event.request)
          onEvent(ManagerUiEvent.LoadFleets)
        }

        is ManagerUiEvent.UpdateFleet -> {
          repository.updateFleet(event.id, event.request)
          onEvent(ManagerUiEvent.LoadFleets)
        }

        is ManagerUiEvent.DeleteFleet -> {
          repository.deleteFleet(event.id)
          onEvent(ManagerUiEvent.LoadFleets)
        }

        // --- VEHICLES ---
        is ManagerUiEvent.LoadVehicles ->
          handleResource(repository.getVehicles()) {
            uiState = uiState.copy(vehicles = it)
          }

        is ManagerUiEvent.CreateVehicle -> {
          repository.createVehicle(event.request)
          onEvent(ManagerUiEvent.LoadVehicles)
        }

        is ManagerUiEvent.UpdateVehicle -> {
          repository.updateVehicle(event.id, event.request)
          onEvent(ManagerUiEvent.LoadVehicles)
        }

        is ManagerUiEvent.DeleteVehicle -> {
          repository.deleteVehicle(event.id)
          onEvent(ManagerUiEvent.LoadVehicles)
        }

        // --- DRIVERS ---
        is ManagerUiEvent.LoadDrivers -> {
          handleResource(repository.getDrivers()) {
            uiState = uiState.copy(drivers = it)
          }
          handleResource(repository.getDriverStats()) {
            uiState = uiState.copy(driverStats = it)
          }
        }

        is ManagerUiEvent.CreateDriver -> {
          repository.createDriver(event.request)
          onEvent(ManagerUiEvent.LoadDrivers)
        }

        is ManagerUiEvent.UpdateDriver -> {
          repository.updateDriver(event.id, event.request)
          onEvent(ManagerUiEvent.LoadDrivers)
        }

        is ManagerUiEvent.DeleteDriver -> {
          repository.deleteDriver(event.id)
          onEvent(ManagerUiEvent.LoadDrivers)
        }

        // --- ASSIGNMENTS ---
        is ManagerUiEvent.LoadAssignments ->
          handleResource(repository.getAssignments()) {
            uiState = uiState.copy(assignments = it)
          }

        is ManagerUiEvent.CreateAssignment -> {
          repository.createAssignment(event.request)
          onEvent(ManagerUiEvent.LoadAssignments)
        }

        is ManagerUiEvent.StartAssignment -> {
          repository.startAssignment(event.id)
          onEvent(ManagerUiEvent.LoadAssignments)
        }

        is ManagerUiEvent.CompleteAssignment -> {
          repository.completeAssignment(event.id)
          onEvent(ManagerUiEvent.LoadAssignments)
        }

        // --- MAINTENANCE ---
        is ManagerUiEvent.LoadMaintenance -> {
          handleResource(repository.getMaintenanceRecords()) {
            uiState = uiState.copy(maintenanceRecords = it)
          }
          handleResource(repository.getOverdueMaintenanceRecords()) {
            uiState = uiState.copy(overdueMaintenance = it)
          }
        }

        is ManagerUiEvent.CreateMaintenanceRecord -> {
          repository.createMaintenanceRecord(event.request)
          onEvent(ManagerUiEvent.LoadMaintenance)
        }

        // --- REPORTS ---
        is ManagerUiEvent.LoadReports ->
          handleResource(repository.getReports()) {
            uiState = uiState.copy(reports = it)
          }

        is ManagerUiEvent.CreateReport -> {
          repository.createReport(event.request)
          onEvent(ManagerUiEvent.LoadReports)
        }
      }
    }
  }

  // --- Reutilizable para cualquier llamada ---
  private suspend fun <T> handleResource(
      resource: Resource<T>,
      onSuccess: (T) -> Unit
  ) {
    when (resource) {
      is Resource.Loading -> uiState = uiState.copy(isLoading = true)
      is Resource.Success -> {
        uiState = uiState.copy(isLoading = false)
        onSuccess(resource.data as T)
      }
      is Resource.Error -> uiState = uiState.copy(isLoading = false, error = resource.message)
      is Resource.Idle<*> -> TODO()
    }
  }
}
