package pe.edu.upc.flota365.domain.usecase

import pe.edu.upc.flota365.features.auth.domain.models.User
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
  private val authRepository: AuthRepository
) {
  suspend operator fun invoke(token: String): User {
    return authRepository.getProfile(token)
  }
}
