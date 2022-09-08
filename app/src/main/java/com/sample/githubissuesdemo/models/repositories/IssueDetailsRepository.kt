package com.sample.githubissuesdemo.models.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.githubissuesdemo.models.RetrofitAPIFactory
import com.sample.githubissuesdemo.models.data.GithubCommentsPagingSource
import com.sample.githubissuesdemo.models.data.GithubFollowersPagingSource
import com.sample.githubissuesdemo.models.entities.Follower
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.response.Resource
import com.sample.githubissuesdemo.models.response.ResponseHandler
import com.sample.githubissuesdemo.models.services.GithubIssuesServices
import kotlinx.coroutines.flow.Flow

class IssueDetailsRepository(private val service: GithubIssuesServices, private val responseHandler : ResponseHandler) : IGIssueDetailRepository {

    override suspend fun getIssueDetails(id: String): Resource<GithubIssue> {
        return try {
            responseHandler.handleSuccess(RetrofitAPIFactory.retrofitAPI().getIssueDetails(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getCommentsList(id: String): Flow<PagingData<GithubIssue>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubCommentsPagingSource(service, id) }
        ).flow
    }

    override suspend fun getFollowersList(userName: String?): Flow<PagingData<Follower>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { GithubFollowersPagingSource(service, userName) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}