package com.sample.githubissuesdemo.models.repositories

import androidx.paging.PagingData
import com.sample.githubissuesdemo.models.entities.GithubIssue
import kotlinx.coroutines.flow.Flow

interface IGithubIssueListRepository {
    suspend fun githubIssueList(): Flow<PagingData<GithubIssue>>
}