package pe.edu.upc.flota365.domain.repository

import pe.edu.upc.flota365.domain.model.Vehicle

interface VehicleRepository {
  suspend fun getAllVehicles(): List<Vehicle>
  // Se podrían añadir más funciones como:
  // suspend fun getVehicleById(id: Int): Vehicle
}
