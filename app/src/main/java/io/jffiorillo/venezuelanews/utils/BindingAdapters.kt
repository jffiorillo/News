package io.jffiorillo.venezuelanews.utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("roundedImg")
    fun setRoundedImage(view: ImageView, url: String?) {

        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .transform(CircleCrop())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(DrawableImageViewTarget(view))
    }

    @JvmStatic
    @BindingAdapter("errorText")
    fun setErrorMessage(view: TextInputLayout, errorStringId: Int?) {
        view.error = errorStringId?.let {
            view.context.getText(errorStringId)
        }
    }

    @JvmStatic
    @BindingAdapter("snackbarMessage")
    fun setSnackbarMessage(view: View, stringId: Int?) {
        stringId?.let { view.showShortSnackbar(view.context.getString(it)) }
    }
}
