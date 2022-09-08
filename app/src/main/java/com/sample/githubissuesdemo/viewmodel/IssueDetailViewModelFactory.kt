package com.sample.githubissuesdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.githubissuesdemo.models.repositories.IGIssueDetailRepository

class IssueDetailViewModelFactory(private val repository: IGIssueDetailRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IssueDetailViewModel(repository) as T
    }
}