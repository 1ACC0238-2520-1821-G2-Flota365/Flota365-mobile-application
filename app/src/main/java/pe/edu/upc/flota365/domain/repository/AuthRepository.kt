package pe.edu.upc.flota365.domain.repository

import pe.edu.upc.flota365.domain.model.User

interface AuthRepository {
  suspend fun login(email: String, password: String): User
  suspend fun register(name: String, email: String, password: String): User
  suspend fun getProfile(userId: String, token: String): User
}
