package io.jffiorillo.venezuelanews.ui.main

import android.os.Bundle
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.base.di.ActivityScope
import io.jffiorillo.venezuelanews.ui.details.ArticleDetailsFragment
import io.jffiorillo.venezuelanews.ui.list.ArticlesListFragment
import io.jffiorillo.venezuelanews.utils.isNull

class MainActivity : DaggerAppCompatActivity() {
  private lateinit var listFragment: ArticlesListFragment
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)


    if (savedInstanceState.isNull()) {
      listFragment = ArticlesListFragment()
      supportFragmentManager.beginTransaction().add(R.id.container, listFragment).commit()
    }
  }

  @dagger.Module
  abstract class Module {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ArticlesListFragment.Module::class, ArticleDetailsFragment.Module::class])
    internal abstract fun mainActivity(): MainActivity
  }
}