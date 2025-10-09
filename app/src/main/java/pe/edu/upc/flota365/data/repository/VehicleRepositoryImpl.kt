package pe.edu.upc.flota365.data.repository

import pe.edu.upc.flota365.data.remote.service.VehicleService
import pe.edu.upc.flota365.domain.model.Vehicle
import pe.edu.upc.flota365.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
  private val api: VehicleService
) : VehicleRepository {

  override suspend fun getAllVehicles(): List<Vehicle> {
    return api.getAllVehicles().map { it.toDomain() }
  }
}
