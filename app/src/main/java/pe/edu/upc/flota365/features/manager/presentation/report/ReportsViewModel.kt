package pe.edu.upc.flota365.features.manager.presentation.report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  // 👇 mutableStateOf hace que Compose reaccione a los cambios
  var uiState by mutableStateOf(ReportsUiState())
    private set

  fun fetchReports() {
    viewModelScope.launch {
      uiState = uiState.copy(isLoading = true, error = null)

      when (val result = repository.getReports()) {
        is Resource.Success -> {
          uiState = uiState.copy(
            isLoading = false,
            reports = result.data ?: emptyList()
          )
        }
        is Resource.Error -> {
          uiState = uiState.copy(
            isLoading = false,
            error = result.message ?: "Error desconocido"
          )
        }
        else -> Unit
      }
    }
  }
}
