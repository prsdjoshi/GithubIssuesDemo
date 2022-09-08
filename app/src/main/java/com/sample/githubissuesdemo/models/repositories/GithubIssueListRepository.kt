package com.sample.githubissuesdemo.models.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sample.githubissuesdemo.models.data.GithubIssuesListPagingSource
import com.sample.githubissuesdemo.models.database.AppDatabase
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.services.GithubIssuesServices
import kotlinx.coroutines.flow.Flow


class GithubIssueListRepository(private val networkService: GithubIssuesServices,
                                private val databaseService: AppDatabase) : IGithubIssueListRepository {

    override suspend fun githubIssueList(): Flow<PagingData<GithubIssue>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                GithubIssuesListPagingSource(networkService, databaseService)
            }
        ).flow
    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}