package pe.edu.upc.flota365.features.auth.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.models.ManagerRegisterRequestDto
import pe.edu.upc.flota365.features.auth.data.remote.models.RegisterUserRequest
import pe.edu.upc.flota365.features.auth.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ManagerRegisterViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  private val _state = MutableStateFlow<Resource<Unit>>(Resource.Idle())
  val state: StateFlow<Resource<Unit>> = _state

  fun registerManager(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    ruc: String,
    businessName: String,
    phone: String
  ) {
    viewModelScope.launch {
      _state.value = Resource.Loading()

      try {
        // solo enviamos los campos que acepta el contexto de autenticación
        val request = ManagerRegisterRequestDto(
          firstName = firstName,
          lastName = lastName,
          email = email,
          password = password,
          role = "Manager"
        )

        // registrar al usuario en el auth context
        val result = repository.registerManagerAndUser(request)

        if (result is Resource.Success<*>) {
          Log.d("ManagerRegisterVM", "Registro de manager exitoso")

          // Guardar localmente los otros datos (si quieres usarlos luego)
          saveLocalManagerData(ruc, businessName, phone)

          _state.value = Resource.Success(Unit)
        } else if (result is Resource.Error<*>) {
          _state.value = Resource.Error(result.message ?: "Error desconocido")
        }

      } catch (e: Exception) {
        _state.value = Resource.Error(e.localizedMessage ?: "Error inesperado")
      }
    }
  }

  private fun saveLocalManagerData(ruc: String, businessName: String, phone: String) {
    // Aquí podrías guardar en DataStore, Room o SharedPreferences si lo necesitas
    Log.d("ManagerRegisterVM", "Datos adicionales -> RUC: $ruc, RS: $businessName, Tel: $phone")
  }
}
