package pe.edu.upc.flota365.features.manager.data.remote.services

import pe.edu.upc.flota365.features.manager.data.remote.models.CreateManagerApiRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ManagerService {

  @POST("Manager")
  suspend fun createManager(@Body request: CreateManagerApiRequest): Response<Unit>
  @GET("Manager")
  suspend fun getManagers(): Response<List<Any>>
}
