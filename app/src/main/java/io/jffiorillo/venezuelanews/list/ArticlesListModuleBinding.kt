package io.jffiorillo.venezuelanews.list

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.jffiorillo.venezuelanews.di.DaggerViewModelInjectionModule
import io.jffiorillo.venezuelanews.di.ViewModelKey

@Module(includes = [DaggerViewModelInjectionModule::class])
abstract class ArticlesListModuleBinding {

  @Binds
  @IntoMap
  @ViewModelKey(ArticlesListViewModel::class)
  abstract fun bindsMainViewModel(myViewModel: ArticlesListViewModel): ViewModel

}