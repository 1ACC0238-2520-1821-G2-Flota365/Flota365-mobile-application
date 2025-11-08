package pe.edu.upc.flota365.features.manager.presentation.report

import pe.edu.upc.flota365.features.manager.data.remote.models.Report

data class ReportsUiState(
  val isLoading: Boolean = false,
  val error: String? = null,
  val reports: List<Report> = emptyList()
)
