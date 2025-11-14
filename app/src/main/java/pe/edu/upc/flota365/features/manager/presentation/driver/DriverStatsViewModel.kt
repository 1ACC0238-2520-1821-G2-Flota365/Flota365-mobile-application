package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.DriverStatsDto
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

data class DriverStatsUiState(
  val isLoading: Boolean = false,
  val stats: DriverStatsDto? = null,
  val error: String? = null
)

@HiltViewModel
class DriverStatsViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  private val _uiState = MutableStateFlow(DriverStatsUiState())
  val uiState = _uiState.asStateFlow()

  init {
    loadDriverStats()
  }

  fun loadDriverStats() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true, error = null)
      when (val result = repository.getDriverStats()) {
        is Resource.Success -> _uiState.value = DriverStatsUiState(stats = result.data)
        is Resource.Error -> _uiState.value = DriverStatsUiState(error = result.message)
        else -> _uiState.value = DriverStatsUiState(error = "Error desconocido")
      }
    }
  }
}
