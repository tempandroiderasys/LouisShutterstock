package io.github.louistsaitszho.louisshutterstock

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    val TAG = "MainFragment"
    private val vm: MainFragmentViewModel by viewModel()
    private val adapter = CategoriesAdapter()
    private val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeAllLiveData()
        setupRecyclerView()
        setupPullToRefresh()
        setupPage()
    }

    /**
     * Once all data stream has been prepared, this trigger data stream to start
     */
    private fun setupPage() {
        //update the UI to indicate that it is loading
        swipe_refresh_layout.isRefreshing = true
        //fetch data
        vm.refreshCategories()
    }

    /**
     * Subscribe to all LiveData (UI streams essentially)
     */
    private fun observeAllLiveData() {
        vm.data.observe(this, Observer {
            it?.run {
                adapter.resetList(this) }
        })

        vm.refreshing.observe(this, Observer {
            when (it) {
                true -> swipe_refresh_layout.isRefreshing = true
                false -> swipe_refresh_layout.isRefreshing = false
                null -> throw IllegalArgumentException("Refreshing can only be true or false")
            }
        })

        vm.errorToast.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * Perform all the necessary init process of RecyclerView
     */
    private fun setupRecyclerView() {
        recycler_view_categories.adapter = adapter
        recycler_view_categories.layoutManager = layoutManager
    }

    /**
     * Setup pull to refresh listener: refresh iff it is not already refreshing
     */
    private fun setupPullToRefresh() {
        swipe_refresh_layout.setOnRefreshListener {
            if (vm.refreshing.value != true)
                vm.refreshCategories()
        }
    }
}

