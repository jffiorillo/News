package io.jffiorillo.venezuelanews.ui.list

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import dagger.Binds
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.base.di.FragmentScope
import io.jffiorillo.venezuelanews.databinding.ArticlesListFragmentBinding
import io.jffiorillo.venezuelanews.di.DataModule
import io.jffiorillo.venezuelanews.ui.details.ArticleDetailsFragment
import io.jffiorillo.venezuelanews.utils.isNotNull
import kotlinx.android.synthetic.main.articles_list_fragment.*
import timber.log.Timber
import javax.inject.Inject

class ArticlesListFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var binding: ArticlesListFragmentBinding

  private val viewModel: ArticlesListViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(
      ArticlesListViewModel::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.articles_list_fragment, container, false)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val activity = activity as AppCompatActivity
    activity.setSupportActionBar(toolbar)
    binding.apply {
      viewmodel = viewModel
      setLifecycleOwner(this@ArticlesListFragment)
    }
    viewModel.start()
    viewModel.repository().observe(this, Observer {
      (rvRepository.adapter as ArticlesAdapter).sourceList = it ?: emptyList()
    })

    androidx.recyclerview.widget.LinearLayoutManager(context).let { ll ->
      rvRepository.adapter = ArticlesAdapter(context!!) { item, view, imageView ->
        Timber.d("Item Selected %s", item)
        val transitionName = ViewCompat.getTransitionName(view)!!
        val imageTransitionName = imageView?.let { ViewCompat.getTransitionName(it) }
        val fragment = ArticleDetailsFragment.newInstance(item, transitionName,
          imageTransitionName, context!!)
        activity.supportFragmentManager?.transaction {
          val sharedElement = addSharedElement(view, transitionName)
          if (imageView.isNotNull()) {
            sharedElement.addSharedElement(imageView!!, imageTransitionName!!)
          }
          sharedElement.addToBackStack(null)
            .replace(R.id.container, fragment)
        }

      }
      rvRepository.layoutManager = ll
    }
  }

  @dagger.Module
  abstract class Module {
    @FragmentScope
    @ContributesAndroidInjector(
      modules = [ArticlesListModuleBinding::class, ArticlesListModule::class, DataModule::class])
    internal abstract fun articlesListFragment(): ArticlesListFragment

    @Binds
    internal abstract fun bindContext(application: Application): Context
  }
}