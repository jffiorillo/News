package io.jffiorillo.venezuelanews

import android.app.Activity
import android.app.Application
import android.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import io.jffiorillo.venezuelanews.di.AppComponent
import io.jffiorillo.venezuelanews.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class App : Application(), HasFragmentInjector, HasActivityInjector {
  override fun fragmentInjector(): DispatchingAndroidInjector<Fragment> = fragmentInjector

  override fun activityInjector(): AndroidInjector<Activity> = activityInjector

  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  @Inject
  lateinit var activityInjector: DispatchingAndroidInjector<Activity>

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
    applicationComponent.inject(this)
    Timber.plant(DebugTree())
    AndroidThreeTen.init(this);
  }
}
