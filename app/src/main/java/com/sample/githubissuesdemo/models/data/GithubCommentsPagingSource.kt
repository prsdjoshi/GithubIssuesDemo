
package com.sample.githubissuesdemo.models.data

import androidx.paging.PagingSource
import com.sample.githubissuesdemo.models.entities.GithubIssue
import com.sample.githubissuesdemo.models.services.GithubIssuesServices
import com.sample.githubissuesdemo.util.GITHUB_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class GithubCommentsPagingSource(
    private val service: GithubIssuesServices,
    private val query: String
) : PagingSource<Int, GithubIssue>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubIssue> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response = service.getCommentsList(query)
            LoadResult.Page(
                    data = response,
                    prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
