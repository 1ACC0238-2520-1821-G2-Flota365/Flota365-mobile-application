package pe.edu.upc.flota365.features.manager.data.remote.models

data class CreateManagerApiRequest(
  val id: String? = null,
  val name: String,
  val email: String,
  val status: String? = "active"
)
