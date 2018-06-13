package io.jffiorillo.venezuelanews

import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import io.jffiorillo.venezuelanews.di.AppComponent
import io.jffiorillo.venezuelanews.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : DaggerApplication(), HasActivityInjector {
  override fun applicationInjector() = applicationComponent

  init {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }

  private val applicationComponent: AppComponent by lazy {
    DaggerAppComponent.builder()
      .application(this)
      .build()
  }

  override fun onCreate() {
    super.onCreate()
    Timber.plant(DebugTree())
    AndroidThreeTen.init(this);
  }
}
