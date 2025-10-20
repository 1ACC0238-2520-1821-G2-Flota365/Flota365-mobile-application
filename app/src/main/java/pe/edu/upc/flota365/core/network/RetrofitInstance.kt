package pe.edu.upc.flota365.core.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

  // ⚠️ Cambia esto por la URL base real de tu backend
  private const val BASE_URL = "https://underground-tuesday-renworkplace-1e2821cb.koyeb.app/swagger/index.html"

  private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  private val httpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .build()

  // Configure Json for Kotlinx Serialization
  private val json = Json {
    ignoreUnknownKeys = true // To prevent crashes if the API adds new fields
    isLenient = true
  }

  private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    // Use the Kotlinx Serialization converter factory
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .client(httpClient)
    .build()

  fun <T> createService(serviceClass: Class<T>): T {
    return retrofit.create(serviceClass)
  }
}
