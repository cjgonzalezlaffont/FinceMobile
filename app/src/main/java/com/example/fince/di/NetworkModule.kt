package com.example.fince.di

import androidx.lifecycle.MutableLiveData
import com.example.fince.core.Config
import com.example.fince.core.InterceptorCustom
import com.example.fince.data.network.FinceApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Utilizar MutableLiveData para apiKey
    val apiKey: MutableLiveData<String> = MutableLiveData(Config.apiKey)

    // Crear una instancia de OkHttpClient.Builder una sola vez
    val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
        addInterceptor(InterceptorCustom)
    }

    // Utilizar una única instancia de OkHttpClient
    var client: OkHttpClient = clientBuilder.build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        // Observar cambios en apiKey y actualizar el encabezado en consecuencia
        apiKey.observeForever {
            client = clientBuilder.addNetworkInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", Config.apiKey)
                    .build()
                chain.proceed(request)
            }).build()
        }

        val baseUrl = Config.baseUrl

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFinceApiClient(retrofit: Retrofit): FinceApiClient {
        return retrofit.create(FinceApiClient::class.java)
        }
}