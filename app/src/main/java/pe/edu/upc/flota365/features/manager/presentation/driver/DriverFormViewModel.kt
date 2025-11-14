package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.utils.Resource
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateDriverDto
import pe.edu.upc.flota365.features.manager.data.remote.models.UpdateDriverDto
import pe.edu.upc.flota365.features.manager.data.repositories.ManagerRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class DriverFormViewModel @Inject constructor(
  private val repository: ManagerRepositoryImpl
) : ViewModel() {

  var isLoading = false
  var errorMessage: String? = null

  fun createDriver(
    firstName: String,
    lastName: String,
    phone: String,
    email: String,
    licenseNumber: String,
    experienceYears: Int = 0,
    onResult: (Boolean) -> Unit
  ) {
    viewModelScope.launch {
      isLoading = true
      val request = CreateDriverDto(
        code = "DRV-${System.currentTimeMillis()}",
        firstName = firstName,
        lastName = lastName,
        licenseNumber = licenseNumber,
        licenseExpiryDate = "2026-01-01T00:00:00Z", // temporal
        phone = phone,
        email = email,
        experienceYears = experienceYears
      )
      val result = repository.createDriver(request)
      isLoading = false
      onResult(result is Resource.Success)
    }
  }

  fun updateDriver(
    id: Int,
    firstName: String,
    lastName: String,
    phone: String,
    email: String,
    licenseNumber: String,
    experienceYears: Int = 0,
    onResult: (Boolean) -> Unit
  ) {
    viewModelScope.launch {
      isLoading = true
      val request = UpdateDriverDto(
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        email = email,
        licenseNumber = licenseNumber,
        licenseExpiryDate = "2026-01-01T00:00:00Z",
        experienceYears = experienceYears,
        assignedVehicle = null
      )
      val result = repository.updateDriver(id, request)
      isLoading = false
      onResult(result is Resource.Success)
    }
  }
}
