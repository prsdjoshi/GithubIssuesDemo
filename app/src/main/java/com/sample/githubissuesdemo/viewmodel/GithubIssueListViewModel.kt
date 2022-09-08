package com.sample.githubissuesdemo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.repositories.IGithubIssueListRepository
import com.sample.githubissuesdemo.util.asLiveData

class GithubIssueListViewModel(private val repository: IGithubIssueListRepository) : ViewModel() {
    var selectedIssue: GithubIssue? = null
    private val issueListLiveData = MediatorLiveData<PagingData<GithubIssue>>()
    val userList = issueListLiveData.asLiveData()


    suspend fun searchUser() {
        issueListLiveData.addSource(
            repository.githubIssueList().cachedIn(viewModelScope).asLiveData()
        ) {
            issueListLiveData.value = it
        }
    }

}