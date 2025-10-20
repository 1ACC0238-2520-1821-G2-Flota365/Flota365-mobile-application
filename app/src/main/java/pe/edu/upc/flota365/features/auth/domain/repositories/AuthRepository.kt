package pe.edu.upc.flota365.features.auth.domain.repositories

import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.domain.models.User

interface AuthRepository {
  suspend fun login(email: String, password: String): Resource<User>
  suspend fun getProfile(token: String): User
}
