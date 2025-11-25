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
import pe.edu.upc.flota365.features.manager.data.remote.services.ManagerService
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
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val trustAllCerts = arrayOf<javax.net.ssl.TrustManager>(
      object : javax.net.ssl.X509TrustManager {
        override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
      }
    )

    val sslContext = javax.net.ssl.SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val sslSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as javax.net.ssl.X509TrustManager)
      .hostnameVerifier { _, _ -> true }
      .build()
  }

  @Provides
  @Singleton
  fun provideGson(): Gson =
    GsonBuilder()
      .create()

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
    Retrofit.Builder()
      .baseUrl("https://flota365-backend-corp-cmawf5ddamh5f7b8.westus3-01.azurewebsites.net/api/")
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()

  @Provides
  @Singleton
  fun provideAuthService(retrofit: Retrofit): AuthService =
    retrofit.create(AuthService::class.java)

  @Provides
  @Singleton
  fun provideManagerService(retrofit: Retrofit): ManagerService =
    retrofit.create(ManagerService::class.java)

}
