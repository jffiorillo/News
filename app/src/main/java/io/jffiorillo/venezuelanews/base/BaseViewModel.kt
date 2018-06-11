package io.jffiorillo.venezuelanews.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

  val disposables: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}