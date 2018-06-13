package io.jffiorillo.venezuelanews.di

import com.squareup.moshi.Moshi
import io.jffiorillo.venezuelanews.base.ExecutionSchedulers
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
import retrofit2.converter.moshi.MoshiConverterFactory
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
  internal fun provideConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

  @Provides
  internal fun provideCallAdapterFactory(
      schedulers: ExecutionSchedulers
  ): CallAdapter.Factory = RxJava2CallAdapterFactory.createWithScheduler(schedulers.io())

  @Provides
  internal fun provideGithubService(retrofit: Retrofit): ArticleApi =
      retrofit.create(ArticleApi::class.java)


  @Provides
  @Named("rootUrl")
  internal fun provideRootUrl(): String = "https://gunow.vcoud.com/"
}