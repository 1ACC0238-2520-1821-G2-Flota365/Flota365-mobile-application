package pe.edu.upc.flota365.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.core.utils.UiState
import pe.edu.upc.flota365.features.auth.domain.models.User
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val repository: AuthRepository
) : ViewModel() {

  private val _email = MutableStateFlow("")
  val email: StateFlow<String> = _email

  private val _password = MutableStateFlow("")
  val password: StateFlow<String> = _password

  private val _user = MutableStateFlow<UiState<User>>(UiState.Initial)
  val user: StateFlow<UiState<User>> = _user

  fun updateEmail(email: String) {
    _email.value = email
  }

  fun updatePassword(password: String) {
    _password.value = password
  }

  fun login() {
    viewModelScope.launch {
      _user.value = UiState.Loading

      val result = repository.login(
        email.value,
        password.value
      )

      _user.value = when (result) {
        is Resource.Success -> {
          result.data?.let { user ->
            UiState.Success(user)
          } ?: UiState.Error("No se pudo obtener la información del usuario")
        }
        is Resource.Error -> UiState.Error(result.message ?: "Error desconocido")
      }
    }
  }
}
