package pe.edu.upc.flota365.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.edu.upc.flota365.data.remote.service.AuthService
import pe.edu.upc.flota365.data.remote.service.ReportService
import pe.edu.upc.flota365.data.remote.service.VehicleService
import pe.edu.upc.flota365.data.repository.AuthRepositoryImpl
import pe.edu.upc.flota365.data.repository.ReportRepositoryImpl
import pe.edu.upc.flota365.data.repository.VehicleRepositoryImpl
import pe.edu.upc.flota365.domain.repository.AuthRepository
import pe.edu.upc.flota365.domain.repository.ReportRepository
import pe.edu.upc.flota365.domain.repository.VehicleRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Define que las dependencias vivirán mientras la app viva.
object AppModule {

  // --- PROVEEDOR PARA LA RED (NETWORKING) ---

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    // Este interceptor nos permite ver en el Logcat las peticiones y respuestas de la API.
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    // <--- CAMBIO IMPORTANTE: Se ha actualizado con la URL de tu API.
    // Se elimina "/swagger/index.html" para tener solo la URL base.
    val BASE_URL = "https://underground-tuesday-renworkplace-1e2821cb.koyeb.app/api/"

    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
  }

  // --- PROVEEDORES DE SERVICIOS DE LA API ---
  // Hilt crea cada servicio que necesites usando la instancia de Retrofit.

  @Provides
  @Singleton
  fun provideAuthService(retrofit: Retrofit): AuthService {
    return retrofit.create(AuthService::class.java)
  }

  @Provides
  @Singleton
  fun provideReportService(retrofit: Retrofit): ReportService {
    return retrofit.create(ReportService::class.java)
  }

  @Provides
  @Singleton
  fun provideVehicleService(retrofit: Retrofit): VehicleService {
    return retrofit.create(VehicleService::class.java)
  }

  // TODO: Añade aquí los demás servicios (FleetsService, MaintenanceService, etc.)

  // --- PROVEEDORES DE REPOSITORIOS ---
  // Hilt construye las implementaciones de los repositorios, proveyendo los servicios que definimos arriba.

  @Provides
  @Singleton
  fun provideAuthRepository(api: AuthService): AuthRepository {
    return AuthRepositoryImpl(api)
  }

  @Provides
  @Singleton
  fun provideReportRepository(api: ReportService): ReportRepository {
    return ReportRepositoryImpl(api)
  }

  @Provides
  @Singleton
  fun provideVehicleRepository(api: VehicleService): VehicleRepository {
    return VehicleRepositoryImpl(api)
  }

  // TODO: Añade aquí los demás repositorios (FleetsRepository, MaintenanceRepository, etc.)
}

