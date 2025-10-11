package pe.edu.upc.flota365.data.repository

import pe.edu.upc.flota365.data.remote.dto.toDomain
import pe.edu.upc.flota365.data.remote.service.ReportService
import pe.edu.upc.flota365.domain.model.Report
import pe.edu.upc.flota365.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
  private val api: ReportService
) : ReportRepository {

  override suspend fun getRecentReports(): List<Report> {
    // Llama a la API, y si la respuesta es exitosa,
    // mapea cada DTO al modelo de dominio.
    return api.getRecentReports().map { it.toDomain() }
  }
}
