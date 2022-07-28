package io.github.thisdk.bootstrap.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.thisdk.bootstrap.config.AppConfig
import io.github.thisdk.bootstrap.data.TestService
import io.github.thisdk.bootstrap.db.AppDatabase
import io.github.thisdk.core.config.CoreConfigInterface
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppConfig.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {
    @Singleton
    @Provides
    fun provideAppConfig(): AppConfig {
        return AppConfig()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CoreConfigModule {
    @Singleton
    @Provides
    fun provideCoreConfig(config: AppConfig): CoreConfigInterface {
        return config
    }
}

@Module
@InstallIn(FragmentComponent::class)
object MJpgTestServiceModule {
    @Provides
    fun provideTestService(retrofit: Retrofit): TestService {
        return retrofit.create(TestService::class.java)
    }
}
