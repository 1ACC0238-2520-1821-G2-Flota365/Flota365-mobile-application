package pe.edu.upc.flota365.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import pe.edu.upc.flota365.features.auth.data.remote.services.AuthService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val BASE_URL = "https://underground-tuesday-renworkplace-1e2821cb.koyeb.app/swagger/"

  @Provides
  @Singleton
  fun provideGson(): Gson = GsonBuilder().setLenient().create()

  @Provides
  @Singleton
  fun provideRetrofit(gson: Gson): Retrofit =
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(OkHttpClient.Builder().build())
      .build()

  @Provides
  @Singleton
  fun provideAuthService(retrofit: Retrofit): AuthService =
    retrofit.create(AuthService::class.java)
}
