package io.jffiorillo.venezuelanews.ui.list

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.base.impl.StoreBuilder
import dagger.Module
import dagger.Provides
import io.jffiorillo.venezuelanews.api.ArticleApi
import io.jffiorillo.venezuelanews.model.Article

@Module
class ArticlesListModule{
    @Provides
  internal fun provideArticleListFecther(
      articleApi: ArticleApi): Fetcher<List<Article>, String> = Fetcher {
    articleApi.fetchArticles()
  }

  @Provides
  internal fun provideArticleStore(
      fetcher: Fetcher<List<Article>, String>): Store<List<Article>, String> =
      StoreBuilder.key<String, List<Article>>().fetcher(fetcher).open()
}