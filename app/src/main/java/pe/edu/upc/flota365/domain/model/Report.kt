package pe.edu.upc.flota365.domain.model

// Modelo de datos limpio que usa la UI y el ViewModel.
data class Report(
  val id: Int,
  val name: String,
  val type: String,
  val creationDate: String, // Podrías usar un tipo de dato más específico como `LocalDate`
  val createdBy: String
)
