package io.jffiorillo.venezuelanews.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.jffiorillo.venezuelanews.base.BaseViewHolder
import io.jffiorillo.venezuelanews.databinding.ArticleItemListBinding
import io.jffiorillo.venezuelanews.model.Article
import io.jffiorillo.venezuelanews.utils.autoNotify
import kotlin.properties.Delegates


class ArticlesAdapter(
  private val context: Context,
  private val itemClick: (Article, View, View?) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
    ArticleViewHolder(
      ArticleItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false), context)

  var sourceList: List<Article> by Delegates.observable(ArrayList()) { _, old, new ->
    autoNotify(old, new) { o, n -> o.id == n.id }
  }

  override fun getItemCount(): Int {
    return sourceList.size
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    when (holder) {
      is ArticleViewHolder -> {
        holder.bind(sourceList[position], itemClick)
      }
    }
  }
}