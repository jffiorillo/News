package io.jffiorillo.venezuelanews.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import io.jffiorillo.venezuelanews.App
import io.jffiorillo.venezuelanews.base.di.ApplicationScope
import io.jffiorillo.venezuelanews.ui.main.MainActivity

@ApplicationScope
@Component(
  modules = [AndroidSupportInjectionModule::class, MainActivity.Module::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

  fun inject(instance: App)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }
}