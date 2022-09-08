package com.sample.githubissuesdemo.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.githubissuesdemo.R
import com.sample.githubissuesdemo.databinding.FragmentCommentsListBinding
import com.sample.githubissuesdemo.databinding.LoadingStateConfigBinding
import com.sample.githubissuesdemo.view.adapters.CommentsListAdapter
import com.sample.githubissuesdemo.viewmodel.IssueDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CommentsListFragment : Fragment() {

    private lateinit var binding: FragmentCommentsListBinding
    private lateinit var loadingStateConfig: LoadingStateConfigBinding

    private val viewModel by lazy {
        parentFragment?.parentFragment?.getViewModel<IssueDetailViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comments_list, container, false)
        loadingStateConfig = binding.loadStateLayout
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            val issueRecyclerViewAdapter = CommentsListAdapter {
            }

            adapter = issueRecyclerViewAdapter
            viewModel?.commentsList?.observe(viewLifecycleOwner, {
                lifecycleScope.launch {
                    issueRecyclerViewAdapter.submitData(it)
                }
            })
            issueRecyclerViewAdapter.addLoadStateListener {
                when (adapter?.itemCount) {
                    0 -> {
                        when (it.refresh) {
                            is LoadState.Loading -> {
                                showNoRecordsAvailableMessage(loading = true)
                            }
                            is LoadState.Error -> {
                                showNoRecordsAvailableMessage(getString(R.string.try_again))
                            }
                            is LoadState.NotLoading -> {
                                showNoRecordsAvailableMessage(getString(R.string.no_records_found))
                            }
                        }
                    }
                    else -> {
                        showNoRecordsAvailableMessage(showList = true)
                    }
                }
            }
        }
    }

    private fun showNoRecordsAvailableMessage(errorMessage : String = "", loading : Boolean = false, showList : Boolean = false) {
        loadingStateConfig.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (showList) View.VISIBLE else View.GONE
        loadingStateConfig.errorMessage.text = errorMessage
        loadingStateConfig.errorMessage.visibility = if (errorMessage.isEmpty()) View.GONE else View.VISIBLE
    }
}