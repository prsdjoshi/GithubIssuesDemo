package com.sample.githubissuesdemo.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.githubissuesdemo.R
import com.sample.githubissuesdemo.databinding.FragmentGithubIssueListBinding
import com.sample.githubissuesdemo.databinding.LoadingStateConfigBinding
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.view.adapters.IssueRecyclerViewAdapter
import com.sample.githubissuesdemo.viewmodel.GithubIssueListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A fragment representing a list of Items.
 */
class UserListFragment : Fragment() {
    private lateinit var binding: FragmentGithubIssueListBinding
    private lateinit var loadingStateConfig: LoadingStateConfigBinding

    private val viewModel: GithubIssueListViewModel by sharedViewModel<GithubIssueListViewModel>()
    private lateinit var issueRecyclerViewAdapter: IssueRecyclerViewAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_github_issue_list, container, false)
        loadingStateConfig = binding.loadStateLayout
        initViewModel()
        initSearchView()
        initRecyclerView()
        return binding.root
    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        viewModel.userList.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                issueRecyclerViewAdapter.submitData(it)
            }
        })
    }

    private fun initSearchView() {
        searchJob = lifecycleScope.launch {
            viewModel.searchUser()
        }
    }

    private fun initRecyclerView() {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            issueRecyclerViewAdapter = IssueRecyclerViewAdapter { user: GithubIssue ->
                onItemClickListener(user)
            }

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
            adapter = issueRecyclerViewAdapter
        }
    }

    private fun showNoRecordsAvailableMessage(errorMessage : String = "", loading : Boolean = false, showList : Boolean = false) {
        loadingStateConfig.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.list.visibility = if (showList) View.VISIBLE else View.GONE
        loadingStateConfig.errorMessage.text = errorMessage
        loadingStateConfig.errorMessage.visibility = if (errorMessage.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun onItemClickListener(item: GithubIssue) {
        viewModel.selectedIssue = item
        findNavController(this).navigate(R.id.action_userListFragment_to_userDetailsFragment)
    }
}