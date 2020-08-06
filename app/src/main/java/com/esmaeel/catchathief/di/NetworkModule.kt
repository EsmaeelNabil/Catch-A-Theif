package com.esmaeel.moviesapp.di

import com.esmaeel.catchathief.BuildConfig
import com.esmaeel.moviesapp.data.remote.NetworkService
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String =
        "https://api.telegram.org/bot1150734821:AAGOoP-8mvV_6UpWYcaNy5Bdgi5n7x0ki68/"


    @Provides
    @Singleton
    @PROFILE_IMAGE_BASE_URL
    fun provideProfileBaseUrl(): String = "https://image.tmdb.org/t/p/w185"

    @Provides
    @Singleton
    @API_KEY
    fun provideApiKey(): String = "0a4b834208c248be5e926aa56b23e6da"


    @Provides
    @Singleton
    fun provideAuthInterceptor(@API_KEY apiKey: String): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            chain.proceed(
                chain.request().newBuilder()
//                    .url(MyUtils.injectApiKey(original, apiKey))
                    .method(original.method, original.body)
                    .build()
            )
        }
    }


    /**
     * @return a Singleton HttpLoggingInterceptor with Beauty Json logger
     *  That Log our network data only in Debug mode.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor(object :
            HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (message.startsWith("{") || message.startsWith("["))
                    Logger.t("JR").json(message)
                else Logger.t("OK_HTTP_MESSAGE_LOGGER").i(message)
            }

        }).setLevel(
            HttpLoggingInterceptor.Level.BODY
        ) else HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.NONE
        )
    }


    /**
     * @param logger HttpLoggingInterceptor
     * @param localInterceptor Interceptor
     * @return OkHttpClient with Auth injector and Logger Interceptors.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logger)
            .build()
    }


    /**
     * @param okHttpClient OkHttpClient with Auth and logger interceptors
     * @param baseUrl String depending on if the app is (Debug - Release).
     * @return NetworkService : Network gateway
     */
    @Provides
    @Singleton
    fun provideNetworkService(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): NetworkService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            /*.addCallAdapterFactory(RxJava2CallAdapterFactory.create())*/
            .baseUrl(baseUrl)
            .build()
            .create(NetworkService::class.java)
    }

}