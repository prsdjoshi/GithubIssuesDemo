package com.sample.githubissuesdemo.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.sample.githubissuesdemo.R
import com.sample.githubissuesdemo.databinding.FragmentIssueDetailsBinding
import com.sample.githubissuesdemo.models.response.Status
import com.sample.githubissuesdemo.util.loadUrl
import com.sample.githubissuesdemo.viewmodel.IssueDetailViewModel
import com.sample.githubissuesdemo.viewmodel.GithubIssueListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : Fragment() {
    private val userListViewModel by sharedViewModel<GithubIssueListViewModel>()
    private val userDetailViewModel by viewModel<IssueDetailViewModel>()
    private lateinit var binding: FragmentIssueDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_issue_details, container, false)
        initViewModel()
        initTabLayout()
        return binding.root
    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        binding.userDetailViewModel = userDetailViewModel
        initSelectedUserDetails()
    }

    private fun initSelectedUserDetails() {
        val selectedUser = userListViewModel.selectedIssue
        userDetailViewModel.loadIssueDetails(
            selectedUser?.number.toString(),
            selectedUser?.user?.login
        )
        binding.imageView.loadUrl(selectedUser?.user?.avatarUrl ?: "")
        binding.loginId.text = selectedUser?.user?.login
        userDetailViewModel.githubIssue.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.ERROR -> Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                else -> {
                }
            }
        })
    }

    private fun initTabLayout() {
        val localNavHost =
            childFragmentManager.findFragmentById(R.id.base_container) as NavHostFragment
        val localController = localNavHost.navController
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(localController.graph.startDestination, false)
            .build()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> localController.navigate(
                        R.id.action_commentsListFragment_to_followerListFragment,
                        null,
                        navOptions
                    )
                    1 -> localController.navigate(
                        R.id.action_followerListFragment_to_commentsListFragment,
                        null,
                        navOptions
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}