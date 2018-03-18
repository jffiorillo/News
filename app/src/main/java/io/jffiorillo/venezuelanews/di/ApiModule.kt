package io.jffiorillo.venezuelanews.di

import io.jffiorillo.venezuelanews.base.ExecutionSchedulers
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.jffiorillo.venezuelanews.BuildConfig
import io.jffiorillo.venezuelanews.api.ArticleApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {
  @Provides
  internal fun provideLoggingInterceptor(): HttpLoggingInterceptor =
      HttpLoggingInterceptor(
          HttpLoggingInterceptor.Logger { message -> Timber.tag("Retrofit").d(message) })
          .apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
          }

  @Provides
  internal fun provideOkHttpClient(
      loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient = OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()

  @Provides
  @Singleton
  internal fun provideRetrofit(
      client: OkHttpClient,
      converterFactory: Converter.Factory,
      callAdapterFactory: CallAdapter.Factory,
      @Named("rootUrl") url: String
  ): Retrofit = Retrofit.Builder()
      .baseUrl(url)
      .addConverterFactory(converterFactory)
      .addCallAdapterFactory(callAdapterFactory)
      .client(client)
      .validateEagerly(BuildConfig.DEBUG)
      .build()

  @Provides
  @Singleton
  internal fun provideConverterFactory(gson: Gson): Converter.Factory =
      GsonConverterFactory.create(gson)

  @Provides
  @Singleton
  internal fun provideCallAdapterFactory(
      schedulers: ExecutionSchedulers
  ): CallAdapter.Factory = RxJava2CallAdapterFactory.createWithScheduler(schedulers.io())

  @Provides
  @Singleton
  internal fun provideGithubService(retrofit: Retrofit): ArticleApi =
      retrofit.create(ArticleApi::class.java)


  @Provides
  @Singleton
  @Named("rootUrl")
  internal fun provideRootUrl(): String = "http://gunow.azurewebsites.net/"
}