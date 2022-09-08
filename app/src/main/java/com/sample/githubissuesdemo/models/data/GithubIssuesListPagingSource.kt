package com.sample.githubissuesdemo.models.data

import android.util.Log
import androidx.paging.PagingSource
import com.sample.githubissuesdemo.models.database.AppDatabase
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.services.GithubIssuesServices
import com.sample.githubissuesdemo.util.ConnectionHelper
import com.sample.githubissuesdemo.util.GITHUB_STARTING_PAGE_INDEX
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

class GithubIssuesListPagingSource(
    private val networkService: GithubIssuesServices,
    databaseService: AppDatabase) : PagingSource<Int, GithubIssue>(), KoinComponent {
    private val userDao = databaseService.githubIssuesDao

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubIssue> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val connectionHelper : ConnectionHelper by inject()
        return try {

            var dBResponse : List<GithubIssue>? = null
            if (!connectionHelper.isOnline()) {
                dBResponse = userDao.getGithubIssuesList()
            } else {
                val response = networkService.githubIssueList()
                dBResponse = response
                dBResponse.isEmpty().let {
                    GlobalScope.launch {
                        userDao.insertAll(dBResponse)
                    }
                }
                Log.d(
                    GithubIssuesListPagingSource::class.java.name,
                    "Loading from Network " + dBResponse.size
                )
            }

            LoadResult.Page(
                data = dBResponse,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (dBResponse.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
