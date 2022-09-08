package com.sample.githubissuesdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.githubissuesdemo.models.repositories.IGithubIssueListRepository

class GithubIssueListViewModelFactory(private val repository: IGithubIssueListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GithubIssueListViewModel(repository) as T
    }
}