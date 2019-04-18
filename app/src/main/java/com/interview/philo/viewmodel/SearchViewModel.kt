package com.interview.philo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.interview.philo.data.remote.model.Resource
import com.interview.philo.repository.MainRepository
import com.interview.philo.view.item.SearchItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    val searchesLiveData = MutableLiveData<Resource<List<SearchItem>>>()
    private val compositeDisposable = CompositeDisposable()

    private val querySubject = PublishSubject.create<String>()

    init {
        compositeDisposable.add(
            querySubject
                .switchMap {
                    return@switchMap mainRepository.search(it).toObservable()
                }
                .map {
                    SearchItem.modelToItem(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    searchesLiveData.postValue(Resource.success(it))
                }, {
                    searchesLiveData.postValue(Resource.error(emptyList(), Error(it)))
                })
        )
    }

    fun search(term: String) {
        querySubject.onNext(term)
    }

    override fun onCleared() {
        super.onCleared()
        this.compositeDisposable.clear()
    }

    companion object {
        private const val Tag = "SearchViewModel"
    }
}
