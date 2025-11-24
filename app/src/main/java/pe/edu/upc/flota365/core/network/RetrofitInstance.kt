package pe.edu.upc.flota365.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
  private const val BASE_URL = "http://flota365-backend-corp-cmawf5ddamh5f7b8.westus3-01.azurewebsites.net/"

  val api: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}
