package com.sample.githubissuesdemo.models.repositories

import androidx.paging.PagingData
import com.sample.githubissuesdemo.models.entities.Follower
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.response.Resource
import kotlinx.coroutines.flow.Flow

interface IGIssueDetailRepository {
    suspend fun getIssueDetails(id: String) :  Resource<GithubIssue>

    suspend fun getCommentsList(id: String) : Flow<PagingData<GithubIssue>>

    suspend fun getFollowersList(userName: String?) : Flow<PagingData<Follower>>

}