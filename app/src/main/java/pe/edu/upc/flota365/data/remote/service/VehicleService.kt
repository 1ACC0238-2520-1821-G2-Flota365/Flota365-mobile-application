package pe.edu.upc.flota365.data.remote.service

import pe.edu.upc.flota365.data.remote.dto.VehicleDto
import retrofit2.http.*

/**
 * Servicio remoto para interactuar con los endpoints relacionados a vehículos.
 * Usa Retrofit para definir las peticiones HTTP al backend.
 */
interface VehicleService {

  // Obtiene todos los vehículos
  @GET("Vehicle")
  suspend fun getAllVehicles(): List<VehicleDto>

  // Obtiene un vehículo específico por ID
  @GET("Vehicle/{id}")
  suspend fun getVehicleById(@Path("id") id: Int): VehicleDto

  // Crea un nuevo vehículo
  @POST("Vehicle")
  suspend fun createVehicle(@Body vehicle: VehicleDto): VehicleDto

  // Actualiza un vehículo existente
  @PUT("Vehicle/{id}")
  suspend fun updateVehicle(
    @Path("id") id: Int,
    @Body vehicle: VehicleDto
  ): VehicleDto

  // Elimina un vehículo por ID
  @DELETE("Vehicle/{id}")
  suspend fun deleteVehicle(@Path("id") id: Int)
}
