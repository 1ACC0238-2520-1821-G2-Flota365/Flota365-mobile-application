package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.DriverDto
import pe.edu.upc.flota365.features.manager.data.remote.models.DriverStatsDto
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  var drivers by mutableStateOf<List<DriverDto>>(emptyList())
    private set

  var stats by mutableStateOf<DriverStatsDto?>(null)
    private set

  var isLoading by mutableStateOf(false)
    private set

  var error by mutableStateOf<String?>(null)
    private set

  fun loadDrivers() {
    viewModelScope.launch {
      isLoading = true
      error = null
      when (val result = repository.getDrivers()) {
        is Resource.Success -> drivers = result.data ?: emptyList()
        is Resource.Error -> error = result.message
        else -> {}
      }
      isLoading = false
    }
  }

  fun loadDriverStats() {
    viewModelScope.launch {
      when (val result = repository.getDriverStats()) {
        is Resource.Success -> stats = result.data
        is Resource.Error -> error = result.message
        else -> {}
      }
    }
  }

  fun deleteDriver(id: Int) {
    viewModelScope.launch {
      when (repository.deleteDriver(id)) {
        is Resource.Success -> loadDrivers()
        is Resource.Error -> error = "Error al eliminar conductor"
        else -> {}
      }
    }
  }
}
