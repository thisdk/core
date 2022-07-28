package io.github.thisdk.core.di

import android.content.Context
import android.graphics.Bitmap
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.thisdk.core.config.CoreConfigInterface
import io.github.thisdk.core.cookie.AppCookieJar
import io.github.thisdk.core.cookie.AppCookieStore
import io.github.thisdk.core.ds.AppDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.net.Proxy
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        config: CoreConfigInterface,
        appCookieJar: AppCookieJar
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(config.networkConnectTimeout(), config.networkTimeoutTimeUnit())
            .readTimeout(config.networkReadTimeout(), config.networkTimeoutTimeUnit())
            .writeTimeout(config.networkWriteTimeout(), config.networkTimeoutTimeUnit())
            .followRedirects(true)
            .cookieJar(appCookieJar)
            .proxy(Proxy.NO_PROXY)
        config.networkInterceptorList().forEach {
            builder.addInterceptor(it)
        }
        return builder.build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        config: CoreConfigInterface
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(config.networkServiceBaseUrl())
            .addConverterFactory(config.retrofitConverterFactory())
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {
    @Singleton
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }.diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }.components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .crossfade(true)
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppDataStoreModule {
    @Singleton
    @Provides
    fun provideAppDataStore(@ApplicationContext context: Context): AppDataStore {
        return AppDataStore(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppCookieJarModule {
    @Singleton
    @Provides
    fun provideAppCookieJar(appDataStore: AppDataStore): AppCookieJar {
        return AppCookieJar(AppCookieStore(appDataStore))
    }
}