package io.jffiorillo.venezuelanews.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

object BindingAdapters {

  @JvmStatic
  @BindingAdapter("roundedImg")
  fun setRoundedImage(view: ImageView, url: String?) {
    val options = RequestOptions().centerCrop().transform(CircleCrop()).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    Glide.with(view.context).load(url).transition(DrawableTransitionOptions.withCrossFade()).apply(options)
      .into(view)
  }

  @JvmStatic
  @BindingAdapter("errorText")
  fun setErrorMessage(view: com.google.android.material.textfield.TextInputLayout, errorStringId: Int?) {
    view.error = errorStringId?.let {
      view.context.getText(it)
    }
  }

  @JvmStatic
  @BindingAdapter("snackbarMessage")
  fun setSnackbarMessage(view: View, stringId: Int?) {
    stringId?.let { Snackbar.make(view, view.context.getString(it), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show() }
  }
}
