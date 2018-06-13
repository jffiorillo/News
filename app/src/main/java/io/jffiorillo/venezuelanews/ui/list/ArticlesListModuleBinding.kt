package io.jffiorillo.venezuelanews.ui.list

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.jffiorillo.venezuelanews.base.di.DaggerViewModelInjectionModule
import io.jffiorillo.venezuelanews.base.di.ViewModelKey

@Module(includes = [DaggerViewModelInjectionModule::class])
abstract class ArticlesListModuleBinding {

  @Binds
  @IntoMap
  @ViewModelKey(ArticlesListViewModel::class)
  abstract fun bindsMainViewModel(myViewModel: ArticlesListViewModel): ViewModel

}