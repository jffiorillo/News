package io.jffiorillo.venezuelanews.api

import io.jffiorillo.venezuelanews.model.Article
import io.reactivex.Single
import retrofit2.http.GET


interface ArticleApi {
  @GET("api-noticias/api/archivo/fuente0.json")
  fun fetchArticles(): Single<List<Article>>
}