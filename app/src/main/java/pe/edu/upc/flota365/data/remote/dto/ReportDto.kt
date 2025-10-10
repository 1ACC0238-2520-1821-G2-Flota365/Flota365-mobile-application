package pe.edu.upc.flota365.data.remote.dto

import pe.edu.upc.flota365.domain.model.Report

// El DTO puede tener nombres de campo diferentes al modelo de dominio.
data class ReportDto(
  val reportId: Int,
  val reportName: String,
  val reportType: String,
  val createdAt: String,
  val authorName: String
)

// Función de mapeo para convertir el DTO al modelo de dominio.
// Esto desacopla tu lógica de negocio de la estructura de la API.
fun ReportDto.toDomain(): Report {
  return Report(
    id = reportId,
    name = reportName,
    type = reportType,
    creationDate = createdAt,
    createdBy = authorName
  )
}
