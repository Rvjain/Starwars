package com.interview.philo.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.interview.philo.data.remote.model.Resource
import com.interview.philo.view.Constants
import com.interview.philo.view.adapter.MainAdapter
import com.interview.philo.view.item.SearchItem
import com.interview.philo.view.viewholder.SearchVH
import com.interview.philo.viewmodel.SearchViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchVH.ItemClickListener {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel
    private val compositeDisposable = CompositeDisposable()

    private val adapter = MainAdapter(this)
    private var currentPage: Int = 1
    var isLastPage = false
    private var isLoading = false

    private val recyclerViewOnScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager

            layoutManager?.let {
                val visibleItemCount = it.childCount
                val totalItemCount = it.itemCount
                val firstVisibleItemPosition = it.findFirstVisibleItemPosition()

                if (isLoading.not() && isLastPage.not()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 10) {
                        searchViewModel.search(et_search.text.toString(), currentPage)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(com.interview.philo.R.layout.activity_main)

        initRecyclerView()

        // create viewmodel
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]
        // observer search live data
        observeSearchLiveData()

        searchOnTextChange()
    }

    private fun initRecyclerView() {
        rv_characters.layoutManager = LinearLayoutManager(this)
        rv_characters.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_characters.adapter = adapter
        rv_characters.setEmptyTextView(tv_empty, com.interview.philo.R.string.empty_text)
        rv_characters.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun searchOnTextChange() {
        compositeDisposable.add(RxTextView.textChangeEvents(et_search)
            .skipInitialValue()
            .filter { it.text().isNotEmpty() }
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                searchViewModel.search(it.text().trim().toString(), currentPage)
            }, {
                Log.e(TAG, it.message)
            }))
    }

    private fun observeSearchLiveData() {
        searchViewModel.searchesLiveData.observe(this, Observer {
            when(it?.status) {
                Resource.Status.SUCCESS -> {
                    isLoading = false
                    it.data?.let { response ->
                        if (isLastPage.not()) {
                            adapter.addItems(SearchItem.modelToItem(response.results))
                        } else {
                            adapter.items = SearchItem.modelToItem(response.results).toMutableList()
                        }

                        if (response.next != null) {
                            isLastPage = false
                            currentPage++
                        } else {
                            isLastPage = true
                            currentPage = 1
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    isLoading = false
                    // todo - Error show error
                }

                Resource.Status.LOADING -> {
                    // todo - Show loading while we are loading the data
                    isLoading = true
                }
            }
        })
    }

    override fun onItemClick(item: SearchItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.PEOPLE_EXTRA_KEY, item)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        rv_characters.removeOnScrollListener(recyclerViewOnScrollListener)
    }

    companion object {
        const val TAG = "MainActivity"
        const val DEBOUNCE_MS = 300L // in ms
    }
}
