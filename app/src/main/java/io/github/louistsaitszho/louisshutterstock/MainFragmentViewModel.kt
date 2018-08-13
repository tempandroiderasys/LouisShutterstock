package io.github.louistsaitszho.louisshutterstock

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.github.louistsaitszho.louisshutterstock.model.Category
import io.github.louistsaitszho.louisshutterstock.model.Repository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainFragmentViewModel(val repo: Repository): ViewModel() {
    private val TAG = "MainFragmentViewModel"
    private val compositeDisposable = CompositeDisposable()

    val data = MutableLiveData<List<Category>>()
    val refreshing = MutableLiveData<Boolean>()
    val errorToast = MutableLiveData<String>()

    /**
     * Ditch all existing categories (and their images) and fetch every again
     */
    fun refreshCategories() {
        val disposableGetCategory = repo.getCategories()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { categories ->
                            data.postValue(categories)
                            refreshing.postValue(false)
                        },
                        { error ->
                            Timber.tag(TAG).d("Failed to fetch categories: $error")
                            errorToast.postValue("Failed to fetch categories: $error")
                            data.postValue(emptyList())
                            refreshing.postValue(false)
                        }
                )
        compositeDisposable.add(disposableGetCategory)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}