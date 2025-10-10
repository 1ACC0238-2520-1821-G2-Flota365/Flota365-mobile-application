package pe.edu.upc.flota365.domain.usecase

import pe.edu.upc.flota365.domain.model.Report
import pe.edu.upc.flota365.domain.repository.ReportRepository
import javax.inject.Inject

// Clase con una única responsabilidad: obtener los reportes.
class GetRecentReportsUseCase @Inject constructor(
  private val repository: ReportRepository
) {
  suspend operator fun invoke(): List<Report> {
    return repository.getRecentReports()
  }
}
