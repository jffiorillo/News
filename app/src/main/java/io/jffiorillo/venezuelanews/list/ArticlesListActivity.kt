package io.jffiorillo.venezuelanews.list

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.databinding.ArticlesListBinding
import kotlinx.android.synthetic.main.articles_list.rvRepository
import org.jetbrains.anko.ctx
import timber.log.Timber
import javax.inject.Inject

class ArticlesListActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: ArticlesListBinding

  private val viewModel: ArticlesListViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(
        ArticlesListViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.articles_list)
    binding.apply {
      viewmodel = viewModel
      setLifecycleOwner(this@ArticlesListActivity)
    }
    viewModel.start()
    viewModel.repository().observe(this, Observer {
      (rvRepository.adapter as ArticlesAdapter).sourceList = it ?: emptyList()
    })

    androidx.recyclerview.widget.LinearLayoutManager(ctx).let { ll ->
      rvRepository.adapter = ArticlesAdapter(this) { item ->
        Timber.d("Item Selected %s", item)
      }
      rvRepository.layoutManager = ll
    }
  }
}