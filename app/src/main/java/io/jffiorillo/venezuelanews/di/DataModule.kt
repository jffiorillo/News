package io.jffiorillo.venezuelanews.di

import android.content.Context
import com.nytimes.android.external.fs3.filesystem.FileSystemFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.jffiorillo.venezuelanews.base.ExecutionSchedulers
import io.jffiorillo.venezuelanews.utils.LocalDateTimeAdapter
import org.threeten.bp.LocalDateTime
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class DataModule {

  @Provides
  fun provideMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(LocalDateTime::class.java, LocalDateTimeAdapter()).build()!!

  @Provides
  internal fun provideCacheDir(context: Context) = FileSystemFactory.create(context.cacheDir)

  @Provides
  internal fun provideExecutionSchedulers() = object : ExecutionSchedulers {}
}