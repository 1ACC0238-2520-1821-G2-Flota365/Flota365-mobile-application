package pe.edu.upc.flota365.features.manager.presentation.monitoring

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.ActiveVehicleDto
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateReportRequest
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class MonitoringViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  var isLoading = mutableStateOf(false)
  var error = mutableStateOf<String?>(null)
  var activeVehicles = mutableStateOf<List<ActiveVehicleDto>>(emptyList())

  init {
    loadActiveVehicles()
  }

  fun loadActiveVehicles() {
    viewModelScope.launch {
      isLoading.value = true
      when (val result = repository.getActiveVehicles()) {
        is Resource.Success -> {
          activeVehicles.value = result.data ?: emptyList()
          error.value = null
        }
        is Resource.Error -> {
          error.value = result.message
        }
        else -> Unit
      }
      isLoading.value = false
    }
  }

  suspend fun createReport(request: CreateReportRequest): Resource<String> =
    withContext(Dispatchers.IO) {
      repository.createReport(request)
    }

  suspend fun deleteVehicle(vehicleId: Int): Resource<Unit> =
    withContext(Dispatchers.IO) {
      repository.deleteVehicle(vehicleId)
    }
}
