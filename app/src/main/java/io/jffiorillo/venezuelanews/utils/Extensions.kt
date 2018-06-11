package io.jffiorillo.venezuelanews.utils

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import io.jffiorillo.venezuelanews.R
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

fun <T> androidx.recyclerview.widget.RecyclerView.Adapter<*>.autoNotify(old: List<T>, new: List<T>, compare: (T, T) -> Boolean) {
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

fun Any?.isNull() = this == null
fun Any?.isNotNull() = !this.isNull()


fun LocalDateTime.comparator(context: Context): String {

  return try {
    val odt = this.atOffset(ZoneOffset.UTC)
    val now = OffsetDateTime.now(ZoneOffset.UTC)
    val d = Duration.between(odt, now)
    when {
      d.seconds / 3600 < 1 -> context.resources.getString(R.string.article_minutes_time, d.seconds / 60)
      else -> context.resources.getString(R.string.article_hours_time, d.seconds / 3600)
    }

  } catch (arithmeticException: ArithmeticException) {
    " "
  }
}