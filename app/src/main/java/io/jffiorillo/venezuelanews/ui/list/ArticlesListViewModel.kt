package io.jffiorillo.venezuelanews.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nytimes.android.external.store3.base.impl.Store
import io.jffiorillo.venezuelanews.R
import io.jffiorillo.venezuelanews.base.BaseViewModel
import io.jffiorillo.venezuelanews.base.ExecutionSchedulers
import io.jffiorillo.venezuelanews.model.Article
import io.reactivex.rxkotlin.addTo
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ArticlesListViewModel
@Inject
constructor(private val source: Store<List<Article>, String>,
            private val schedulers: ExecutionSchedulers)
  : BaseViewModel() {

  private val repository: MutableLiveData<List<Article>> = MutableLiveData()
  private val isLoading = MutableLiveData<Boolean>()
  private val isFailing = MutableLiveData<Boolean>()
  private val snackbarMessage = MutableLiveData<Int>()

  fun repository(): LiveData<List<Article>> = repository
  fun isLoading(): LiveData<Boolean> = isLoading
  fun isFailing(): LiveData<Boolean> = isFailing
  fun snackbarMessage(): MutableLiveData<Int> = snackbarMessage;


  fun start() {
    if (repository.value?.isEmpty() != false) {
      searchRepositories()
    }
  }

  private fun searchRepositories() {
    source.fetch("1")
      .subscribeOn(schedulers.io()).observeOn(schedulers.ui())
      .doOnSubscribe {
        isFailing.value = false
        isLoading.value = true
      }.doAfterTerminate { isLoading.value = false }
      .subscribe(
        { repository.value = repository.value.orEmpty().plus(it) },
        {
          isFailing.value = true
          handleError(it)
        }
      )
      .addTo(disposables)
  }

  fun refreshRepositories() {
    repository.value = emptyList()
    searchRepositories()
  }

  private fun handleError(t: Throwable) {
    when (t) {
      is HttpException -> {
      }
      is IOException -> {
      }
      else -> {
        snackbarMessage.setValue(R.string.default_error)
      }
    }
    Timber.e(t)
  }
}