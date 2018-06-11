package io.jffiorillo.venezuelanews.list

import android.content.Context
import android.graphics.Bitmap
import android.view.View
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
                        private val activityContext: Context) : BaseViewHolder(binding) {
  private val size = activityContext.resources.getDimensionPixelSize(R.dimen.item_image_size)
  private val radius = activityContext.resources.getInteger(R.integer.image_item_radius)

  fun bind(article: Article, itemClick: (Article) -> Unit) {
    binding.setVariable(BR.article, article)
    binding.root.setOnClickListener { itemClick.invoke(article) }
    binding.imageView.visibility = View.GONE
    if (article.imageUrl.isNotNullAndNotEmpty()) {
      val options = RequestOptions().override(size).placeholder(R.drawable.article_item_placeholder)
        .transforms(CenterCrop(), RoundedCorners(radius))
      Glide.with(activityContext)
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
    binding.date.text = article.date?.comparator(activityContext) ?: ""
    binding.executePendingBindings()
  }
}