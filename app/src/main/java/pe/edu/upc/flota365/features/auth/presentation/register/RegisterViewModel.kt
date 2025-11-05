package pe.edu.upc.flota365.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.features.auth.data.remote.models.*
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import pe.edu.upc.flota365.features.manager.data.remote.models.ManagerRegisterRequestDto
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val repository: AuthRepository
) : ViewModel() {

  fun registerDriver(
    firstName: String,
    lastName: String,
    dni: String,
    license: String,
    email: String,
    phone: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    viewModelScope.launch {
      try {
        val response = repository.registerDriver(
          DriverRegisterRequestDto(firstName, lastName, dni, license, email, phone, password)
        )
        if (response.isSuccessful) {
          onSuccess()
        } else {
          onError("Error al registrar: ${response.message()}")
        }
      } catch (e: Exception) {
        onError(e.message ?: "Error desconocido")
      }
    }
  }

  fun registerManager(
    firstName: String,
    lastName: String,
    ruc: String,
    businessName: String,
    email: String,
    phone: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
  ) {
    viewModelScope.launch {
      try {
        // Solo se envían los campos que el backend de autenticación acepta
        val request = ManagerRegisterRequestDto(
          firstName = firstName,
          lastName = lastName,
          email = email,
          password = password,
          role = "Manager"
        )

        val response = repository.registerManager(request)

        if (response.isSuccessful) {
          // Aquí puedes guardar localmente los otros datos si los necesitas
          saveLocalManagerData(ruc, businessName, phone)
          onSuccess()
        } else {
          onError("Error al registrar: ${response.message()}")
        }
      } catch (e: Exception) {
        onError(e.message ?: "Error desconocido")
      }
    }
  }
}
private fun saveLocalManagerData(ruc: String, businessName: String, phone: String) {
  // Guardar temporalmente los datos adicionales, si quieres usarlos más tarde.
  // Por ejemplo, con DataStore, Room o simplemente loguearlos:
  println("Datos adicionales -> RUC: $ruc, Razón Social: $businessName, Teléfono: $phone")
}
