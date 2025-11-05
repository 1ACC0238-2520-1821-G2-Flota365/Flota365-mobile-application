package pe.edu.upc.flota365.features.auth.domain.repositories

import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.models.DriverRegisterRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.models.LoginResponseDto
import pe.edu.upc.flota365.features.manager.data.remote.models.ManagerRegisterRequestDto
import pe.edu.upc.flota365.features.auth.domain.models.User
import retrofit2.Response

interface AuthRepository {
  suspend fun login(email: String, password: String): Resource<User>
  suspend fun getProfile(token: String): User

  suspend fun registerDriver(request: DriverRegisterRequestDto): Response<LoginResponseDto>

  suspend fun registerManager(request: ManagerRegisterRequestDto): Response<LoginResponseDto>
}
