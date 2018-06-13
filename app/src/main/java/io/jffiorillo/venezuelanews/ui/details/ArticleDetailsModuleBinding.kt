package io.jffiorillo.venezuelanews.ui.details

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.jffiorillo.venezuelanews.base.di.DaggerViewModelInjectionModule
import io.jffiorillo.venezuelanews.base.di.ViewModelKey
import io.jffiorillo.venezuelanews.ui.list.ArticlesListViewModel

@Module(includes = [DaggerViewModelInjectionModule::class])
abstract class ArticleDetailsModuleBinding {

  @Binds
  @IntoMap
  @ViewModelKey(ArticleDetailsViewModel::class)
  abstract fun bindsMainViewModel(myViewModel: ArticleDetailsViewModel): ViewModel

}