package pe.edu.upc.flota365.presentation.ui.gestor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.domain.model.Report
import pe.edu.upc.flota365.domain.usecase.GetRecentReportsUseCase
import javax.inject.Inject

// 1. Estado de la UI: Representa todos los datos que la pantalla necesita.
data class ReportsUiState(
  val recentReports: List<Report> = emptyList(),
  val isLoading: Boolean = false,
  // Ejemplo de futuros filtros:
  // val reportTypes: List<String> = emptyList(),
  // val selectedType: String? = null
)

// 2. ViewModel con Hilt para inyección de dependencias
@HiltViewModel
class ReportsViewModel @Inject constructor(
  private val getRecentReportsUseCase: GetRecentReportsUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow(ReportsUiState())
  val uiState: StateFlow<ReportsUiState> = _uiState

  init {
    fetchRecentReports()
  }

  private fun fetchRecentReports() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true)
      try {
        val reports = getRecentReportsUseCase()
        _uiState.value = _uiState.value.copy(
          recentReports = reports,
          isLoading = false
        )
      } catch (e: Exception) {
        _uiState.value = _uiState.value.copy(isLoading = false)
        // Aquí podrías manejar errores con un snackbar o log
      }
    }
  }
}
