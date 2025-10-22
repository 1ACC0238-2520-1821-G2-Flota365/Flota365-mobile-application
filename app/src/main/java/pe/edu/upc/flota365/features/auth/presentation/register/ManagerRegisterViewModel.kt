package pe.edu.upc.flota365.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.auth.data.remote.models.ManagerRegisterRequestDto
import pe.edu.upc.flota365.features.auth.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ManagerRegisterViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  private val _state = MutableStateFlow<Resource<Unit>>(Resource.Loading<Unit>())
  val state: StateFlow<Resource<Unit>> = _state

  fun registerManager(
    firstName: String,
    lastName: String,
    ruc: String,
    businessName: String,
    email: String,
    phone: String,
    password: String
  ) {
    viewModelScope.launch {
      _state.value = Resource.Loading<Unit>()

      val request = ManagerRegisterRequestDto(
        firstName = firstName,
        lastName = lastName,
        ruc = ruc,
        businessName = businessName,
        email = email,
        phone = phone,
        password = password
      )

      _state.value = repository.registerManager(request)
    }
  }
}
