package com.sample.githubissuesdemo.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sample.githubissuesdemo.models.entities.Follower
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.repositories.IGIssueDetailRepository
import com.sample.githubissuesdemo.models.response.Resource
import com.sample.githubissuesdemo.util.asLiveData
import kotlinx.coroutines.launch

class IssueDetailViewModel(private var repository: IGIssueDetailRepository) : ViewModel() {
    private var lastSearch: String? = ""
    private val githubIssueLiveData = MediatorLiveData<Resource<GithubIssue>>()
    private val commentsListLiveData = MediatorLiveData<PagingData<GithubIssue>>()
    private val followerListLiveData = MediatorLiveData<PagingData<Follower>>()

    //Expose Readable data for View
    val githubIssue = githubIssueLiveData.asLiveData()
    val commentsList = commentsListLiveData.asLiveData()
    val followerList = followerListLiveData.asLiveData()

    fun loadIssueDetails(it: String?, userName: String?) {
        if (lastSearch == it) {
            return
        }
        lastSearch = it
        it?.let {
            viewModelScope.launch {
                getIssueDetails(it)
                getUserComments(it)
                getUserFollowers(userName)
            }
        }
    }

    private suspend fun getUserComments(id: String) {
        commentsListLiveData.addSource(repository.getCommentsList(id).cachedIn(viewModelScope).asLiveData()) {
            commentsListLiveData.value = it
        }
    }
    private suspend fun getUserFollowers(userName: String?) {
        followerListLiveData.addSource(repository.getFollowersList(userName).cachedIn(viewModelScope).asLiveData()) {
            followerListLiveData.value = it
        }
    }

    private suspend fun getIssueDetails(id: String) {
        githubIssueLiveData.value = repository.getIssueDetails(id)
    }

    public fun getBody() : String{
        return githubIssue?.value?.data?.body?.substring(0, Math.min(githubIssue?.value?.data?.body !!.length, 200)) + "..."
    }
}