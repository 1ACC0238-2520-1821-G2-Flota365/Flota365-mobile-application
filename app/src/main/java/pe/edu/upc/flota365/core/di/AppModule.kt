package pe.edu.upc.flota365.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import pe.edu.upc.flota365.features.auth.data.repositories.AuthRepositoryImpl
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  // ... (OkHttpClient providers remain the same)
  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

  @Provides
  @Singleton
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()


  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val BASE_URL = "https://underground-tuesday-renworkplace-1e2821cb.koyeb.app/api/"
    val contentType = "application/json".toMediaType()

    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      // Replaced Moshi with Kotlinx Serialization converter
      .addConverterFactory(Json.asConverterFactory(contentType))
      .build()
  }

  // ... (Auth feature providers remain the same)
  @Provides
  @Singleton
  fun provideAuthService(retrofit: Retrofit): AuthService =
    retrofit.create(AuthService::class.java)

  @Provides
  @Singleton
  fun provideAuthRepository(service: AuthService): AuthRepository =
    AuthRepositoryImpl(service)
}
