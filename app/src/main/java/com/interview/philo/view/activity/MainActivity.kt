package com.interview.philo.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.interview.philo.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        rv_characters.setEmptyTextView(tv_empty, R.string.empty_text)
    }

    private fun searchOnTextChange() {
        compositeDisposable.add(RxTextView.textChangeEvents(et_search)
            .skipInitialValue()
            .filter { it.text().isNotEmpty() }
            .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                searchViewModel.search(it.text().trim().toString())
            }, {
                Log.e(TAG, it.message)
            }))
    }

    private fun observeSearchLiveData() {
        searchViewModel.searchesLiveData.observe(this, Observer {
            when(it?.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { items ->
                        adapter.items = items
                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

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
    }

    companion object {
        const val TAG = "MainActivity"
        const val DEBOUNCE_MS = 300L // in ms
    }
}
