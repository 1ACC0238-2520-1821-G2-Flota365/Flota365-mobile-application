// pe.edu.upc.flota365.features.auth.data.repositories.ManagerRepositoryImpl.kt
package pe.edu.upc.flota365.features.auth.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.models.CreateManagerApiRequest
import pe.edu.upc.flota365.features.auth.data.remote.models.ManagerRegisterRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.services.ManagerService
import javax.inject.Inject

class ManagerRepositoryImpl @Inject constructor(
  private val service: ManagerService
) {

  suspend fun registerManager(request: ManagerRegisterRequestDto): Resource<Unit> =
    withContext(Dispatchers.IO) {
      try {
        val apiRequest = CreateManagerApiRequest(
          name = "${request.firstName} ${request.lastName} - ${request.businessName}",
          email = request.email
        )

        val response = service.createManager(apiRequest)

        if (response.isSuccessful) {
          Resource.Success(Unit)
        } else {
          Resource.Error("Error HTTP ${response.code()}: ${response.message()}")
        }
      } catch (e: Exception) {
        Resource.Error(e.localizedMessage ?: "Error desconocido")
      }
    }
}
