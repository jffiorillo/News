package io.jffiorillo.venezuelanews.ui.details

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import dagger.Binds
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.base.di.FragmentScope
import io.jffiorillo.venezuelanews.di.DataModule
import io.jffiorillo.venezuelanews.model.Article
import io.jffiorillo.venezuelanews.utils.isNotNull
import kotlinx.android.synthetic.main.article_details_fragment.*
import timber.log.Timber
import javax.inject.Inject


private const val ARTICLE_KEY = "article_key"
private const val TITLE_TRANSITION_NAME = "title_transition_name"
private const val IMAGE_TRANSITION_NAME = "image_transition_name"

class ArticleDetailsFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val article: Article by lazy { arguments!!.getParcelable<Article>(ARTICLE_KEY) }
  private val titleTransitionName: String by lazy { arguments!!.getString(TITLE_TRANSITION_NAME) }
  private val imageTransitionName: String? by lazy { arguments!!.getString(IMAGE_TRANSITION_NAME) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.article_details_fragment, container, false)
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val compatActivity = activity as AppCompatActivity
    compatActivity.setSupportActionBar(toolbar)
    toolbar_text.text = article.title
    ViewCompat.setTransitionName(toolbar_text, titleTransitionName)
    if (imageTransitionName.isNotNull()) {
      ViewCompat.setTransitionName(toolbar_image, imageTransitionName!!)
      postponeEnterTransition()
      val requestOptions = RequestOptions().dontAnimate()
      Glide.with(this).asBitmap().apply(requestOptions)
        .load(article.imageUrl)
        .listener(object : RequestListener<Bitmap> {
          override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
            toolbar_image.visibility = View.GONE
            startPostponedEnterTransition()
            Timber.e(e)
            return false
          }

          override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            toolbar_image.visibility = View.VISIBLE
            startPostponedEnterTransition()
            return false
          }
        })
        .into(toolbar_image)
    }
  }

  companion object {
    fun newInstance(article: Article, titleTransitionName: String, imageTransitionName: String?, context: Context): ArticleDetailsFragment {
      val fragment = ArticleDetailsFragment()
      val bundle = Bundle()
      bundle.putParcelable(ARTICLE_KEY, article)
      bundle.putString(TITLE_TRANSITION_NAME, titleTransitionName)
      imageTransitionName?.let { bundle.putString(IMAGE_TRANSITION_NAME, it) }
      fragment.arguments = bundle
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        fragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
      }
      return fragment
    }
  }

  @dagger.Module
  abstract class Module {
    @FragmentScope
    @ContributesAndroidInjector(
      modules = [ArticleDetailsModuleBinding::class, DataModule::class])
    internal abstract fun articleDetailsFragment(): ArticleDetailsFragment

    @Binds
    internal abstract fun bindContext(application: Application): Context
  }
}