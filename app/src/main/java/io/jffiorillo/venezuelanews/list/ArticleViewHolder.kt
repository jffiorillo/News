package io.jffiorillo.venezuelanews.list

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.jffiorillo.venezuelanews.BR
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.base.BaseViewHolder
import io.jffiorillo.venezuelanews.databinding.ArticleItemListBinding
import io.jffiorillo.venezuelanews.model.Article
import io.jffiorillo.venezuelanews.utils.comparator
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

class ArticleViewHolder(private val binding: ArticleItemListBinding,
                        private val activityContext: Context) : BaseViewHolder(binding) {
  private val size = activityContext.resources.getDimensionPixelSize(R.dimen.item_image_size)
  private val radius = activityContext.resources.getInteger(R.integer.image_item_radius)

  fun bind(article: Article, itemClick: (Article) -> Unit) {
    binding.setVariable(BR.article, article)
    binding.root.setOnClickListener { itemClick.invoke(article) }
    if (!article.imageUrl.isNullOrEmpty()) {
      val options = RequestOptions().override(size).placeholder(R.drawable.article_item_placeholder)
        .transforms(CenterCrop(), RoundedCorners(radius))
      Glide.with(activityContext).asBitmap()
        .apply(options)
        .load(article.imageUrl).into(binding.imageView)
    } else {
      binding.imageView.setBackgroundResource(R.drawable.article_item_placeholder)
    }
    binding.source.text = article.sourceName
    binding.date.text = article.date?.comparator(activityContext) ?: ""
    binding.executePendingBindings()
  }
}