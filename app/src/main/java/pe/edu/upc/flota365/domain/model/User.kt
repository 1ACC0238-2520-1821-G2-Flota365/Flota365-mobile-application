package pe.edu.upc.flota365.domain.model

data class User(
  val id: Int,
  val name: String,
  val email: String,
  val token: String? = null
)
