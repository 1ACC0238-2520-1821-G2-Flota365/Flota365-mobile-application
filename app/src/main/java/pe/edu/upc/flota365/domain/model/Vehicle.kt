package pe.edu.upc.flota365.domain.model

// Modelo de dominio simple que usará la UI
data class Vehicle(
  val id: Int,
  val licensePlate: String,
  val brand: String,
  val model: String,
  val year: Int,
  val status: String
)
