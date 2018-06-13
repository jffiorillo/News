package io.jffiorillo.venezuelanews.ui.list

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.jffiorillo.venezuelanews.BR
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.base.BaseViewHolder
import io.jffiorillo.venezuelanews.databinding.ArticleItemListBinding
import io.jffiorillo.venezuelanews.model.Article
import io.jffiorillo.venezuelanews.utils.comparator
import io.jffiorillo.venezuelanews.utils.isNotNullAndNotEmpty
import timber.log.Timber

class ArticleViewHolder(private val binding: ArticleItemListBinding,
                        private val context: Context) : BaseViewHolder(binding) {
  private val size = context.resources.getDimensionPixelSize(R.dimen.item_image_size)
  private val radius = context.resources.getInteger(R.integer.image_item_radius)

  fun bind(article: Article, itemClick: (Article, View, View?) -> Unit) {
    binding.setVariable(BR.article, article)
    ViewCompat.setTransitionName(binding.title, "title ${article.genId()}")
    binding.root.setOnClickListener { itemClick.invoke(article, binding.title, getAndConfigureImage(article)) }
    binding.imageView.visibility = View.GONE
    if (article.imageUrl.isNotNullAndNotEmpty()) {
      val options = RequestOptions().override(size).placeholder(R.drawable.article_item_placeholder)
        .transforms(CenterCrop(), RoundedCorners(radius))
      Glide.with(context)
        .asBitmap().apply(options)
        .listener(object : RequestListener<Bitmap> {
          override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
            binding.imageView.visibility = View.GONE
            Timber.e(e)
            return false
          }

          override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            binding.imageView.visibility = View.VISIBLE
            return false
          }
        })
        .load(article.imageUrl).into(binding.imageView)
    }
    binding.date.text = article.date?.comparator(context) ?: ""
    binding.executePendingBindings()
  }

  private fun getAndConfigureImage(article: Article) = if (article.imageUrl.isNotNullAndNotEmpty() && binding.imageView.visibility == View.VISIBLE) {
    ViewCompat.setTransitionName(binding.imageView, "image ${article.genId()}")
    binding.imageView
  } else {
    null
  }

}