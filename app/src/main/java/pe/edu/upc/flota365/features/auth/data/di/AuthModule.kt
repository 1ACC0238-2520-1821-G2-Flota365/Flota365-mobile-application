package pe.edu.upc.flota365.features.auth.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upc.flota365.features.auth.domain.repositories.AuthRepository
import pe.edu.upc.flota365.features.auth.data.repositories.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

  @Binds
  @Singleton
  abstract fun bindAuthRepository(
    impl: AuthRepositoryImpl
  ): AuthRepository
}
