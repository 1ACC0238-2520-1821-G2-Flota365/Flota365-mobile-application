package pe.edu.upc.flota365.domain.usecase

import pe.edu.upc.flota365.domain.model.User
import pe.edu.upc.flota365.domain.repository.AuthRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
  private val authRepository: AuthRepository
) {
  suspend operator fun invoke(userId: String, token: String): User {
    return authRepository.getProfile(userId, token)
  }
}
