package pe.edu.upc.flota365.features.manager.presentation.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateFleetRequest
import pe.edu.upc.flota365.features.manager.data.remote.models.FleetDto
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

data class FleetUiState(
  val isLoading: Boolean = false,
  val fleets: List<FleetDto> = emptyList(),
  val error: String? = null,
  val message: String? = null
)

@HiltViewModel
class FleetViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  var uiState by mutableStateOf(FleetUiState())
    private set

  fun loadFleets() {
    viewModelScope.launch {
      uiState = uiState.copy(isLoading = true, error = null)
      when (val result = repository.getFleets()) {
        is Resource.Success -> uiState = uiState.copy(isLoading = false, fleets = result.data ?: emptyList())
        is Resource.Error -> uiState = uiState.copy(isLoading = false, error = result.message ?: "Error al obtener flotas")
        else -> Unit
      }
    }
  }

  fun createFleet(request: CreateFleetRequest) {
    viewModelScope.launch {
      uiState = uiState.copy(isLoading = true)
      when (val result = repository.createFleet(request)) {
        is Resource.Success -> {
          loadFleets()
          uiState = uiState.copy(isLoading = false, message = "Flota creada correctamente ✅")
        }
        is Resource.Error -> {
          uiState = uiState.copy(isLoading = false, error = result.message ?: "Error al crear flota ❌")
        }
        else -> Unit
      }
    }
  }

  fun deleteFleet(id: Int) {
    viewModelScope.launch {
      uiState = uiState.copy(isLoading = true)
      when (val result = repository.deleteFleet(id)) {
        is Resource.Success -> {
          loadFleets()
          uiState = uiState.copy(isLoading = false, message = "Flota eliminada correctamente 🗑️")
        }
        is Resource.Error -> {
          uiState = uiState.copy(isLoading = false, error = result.message ?: "Error al eliminar flota ❌")
        }
        else -> Unit
      }
    }
  }

  fun clearMessages() {
    uiState = uiState.copy(message = null, error = null)
  }
}
