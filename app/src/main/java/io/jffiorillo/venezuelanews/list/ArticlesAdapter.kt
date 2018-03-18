package io.jffiorillo.venezuelanews.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.jffiorillo.venezuelanews.BR
import io.jffiorillo.venezuelanews.base.BaseViewHolder
import io.jffiorillo.venezuelanews.databinding.ArticleItemListBinding
import io.jffiorillo.venezuelanews.model.Article
import kotlin.properties.Delegates

class ArticlesAdapter(
    private val itemClick: (Article) -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
      ArticleViewHolder(
          ArticleItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  var sourceList: List<Article> by Delegates.observable(ArrayList()) { _, old, new ->
    autoNotify(old, new) { o, n -> o.id == n.id }
  }

  fun <T> RecyclerView.Adapter<*>.autoNotify(old: List<T>, new: List<T>,
      compare: (T, T) -> Boolean) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

      override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return compare(old[oldItemPosition], new[newItemPosition])
      }

      override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
      }

      override fun getOldListSize() = old.size

      override fun getNewListSize() = new.size
    })

    diff.dispatchUpdatesTo(this)
  }

  override fun getItemCount(): Int {
    return sourceList.size
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    when (holder) {
      is ArticleViewHolder -> {
        holder.binding.setVariable(BR.article, sourceList[position])
        holder.binding.root.setOnClickListener { itemClick.invoke(sourceList[position]) }
      }
    }
    holder.binding.executePendingBindings()
  }

  class ArticleViewHolder(binding: ArticleItemListBinding) : BaseViewHolder(binding)

}