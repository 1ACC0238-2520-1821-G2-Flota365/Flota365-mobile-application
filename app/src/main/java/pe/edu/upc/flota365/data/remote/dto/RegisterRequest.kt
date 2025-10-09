package pe.edu.upc.flota365.data.remote.dto

import com.squareup.moshi.Json

/**
 * DTO usado para enviar los datos del usuario que se va a registrar.
 * Este objeto se envía en el cuerpo de la solicitud POST al endpoint /Auth/register.
 */
data class RegisterRequest(
  @Json(name = "name") val name: String,
  @Json(name = "email") val email: String,
  @Json(name = "password") val password: String
)
