package pe.edu.upc.flota365.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService
import pe.edu.upc.flota365.features.auth.data.repositories.AuthRepositoryImpl
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
  fun provideGson(): Gson =
    GsonBuilder()
      .create()

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    val baseUrl = "https://underground-tuesday-renworkplace-1e2821cb.koyeb.app/api/"

    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
  }

  @Provides
  @Singleton
  fun provideAuthService(retrofit: Retrofit): AuthService =
    retrofit.create(AuthService::class.java)

  @Provides
  @Singleton
  fun provideAuthRepository(service: AuthService): AuthRepository =
    AuthRepositoryImpl(service)
}
