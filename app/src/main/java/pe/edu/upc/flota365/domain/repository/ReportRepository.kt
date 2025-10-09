package pe.edu.upc.flota365.domain.repository

import pe.edu.upc.flota365.domain.model.Report

// La interfaz define QUÉ se puede hacer, pero no CÓMO.
interface ReportRepository {
  suspend fun getRecentReports(): List<Report>
  // Podrías añadir más funciones aquí:
  // suspend fun generateReport(filters: ReportFilters): File
}
