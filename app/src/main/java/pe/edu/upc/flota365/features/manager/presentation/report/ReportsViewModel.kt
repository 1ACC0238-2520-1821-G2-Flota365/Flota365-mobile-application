package pe.edu.upc.flota365.features.manager.presentation.report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateReportRequest
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  var uiState by mutableStateOf(ReportsUiState())
    private set

  fun fetchReports() {
    viewModelScope.launch {
      uiState = uiState.copy(isLoading = true, error = null)

      when (val result = repository.getReports()) {
        is Resource.Success -> uiState = uiState.copy(isLoading = false, reports = result.data ?: emptyList())
        is Resource.Error -> uiState = uiState.copy(isLoading = false, error = result.message ?: "Error desconocido")
        else -> Unit
      }
    }
  }

  suspend fun createReport(request: CreateReportRequest): Resource<String> =
    withContext(Dispatchers.IO) {
      try {
        when (val result = repository.createReport(request)) {
          is Resource.Success -> {
            fetchReports()
            Resource.Success("Reporte generado correctamente")
          }
          is Resource.Error -> Resource.Error(result.message ?: "Error al generar reporte")
          else -> Resource.Error("Error desconocido")
        }
      } catch (e: Exception) {
        Resource.Error("Error: ${e.localizedMessage}")
      }
    }
}
