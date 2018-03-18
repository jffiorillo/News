package io.jffiorillo.venezuelanews.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.jffiorillo.venezuelanews.list.ArticlesListActivity
import io.jffiorillo.venezuelanews.list.ArticlesListModule
import io.jffiorillo.venezuelanews.list.ArticlesListModuleBinding


@Module(includes = [DataModule::class])
abstract class AppModule {

  @ContributesAndroidInjector(
      modules = [ArticlesListModuleBinding::class, ArticlesListModule::class, DaggerViewModelInjectionModule::class])
  internal abstract fun articlesListActivity(): ArticlesListActivity

  @Binds
  internal abstract fun bindContext(application: Application): Context

}