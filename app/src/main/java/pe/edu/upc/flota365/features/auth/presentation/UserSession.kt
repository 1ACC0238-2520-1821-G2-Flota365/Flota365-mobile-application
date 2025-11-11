package pe.edu.upc.flota365.features.auth.presentation

import pe.edu.upc.flota365.features.auth.domain.models.User

object UserSession {
  var currentUser: User? = null
  fun clear() {
    currentUser = null
  }
}
