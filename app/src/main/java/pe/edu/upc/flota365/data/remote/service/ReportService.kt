package pe.edu.upc.flota365.data.remote.service

import pe.edu.upc.flota365.data.remote.dto.ReportDto
import retrofit2.http.GET

interface ReportService {
  @GET("reports/recent") // Ejemplo de endpoint
  suspend fun getRecentReports(): List<ReportDto>
}
