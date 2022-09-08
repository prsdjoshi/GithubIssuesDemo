package com.sample.githubissuesdemo.models.database.daos

import androidx.room.*
import com.sample.githubissuesdemo.models.entities.GithubIssue

@Dao
interface GithubIssuesDao {
    @Query("SELECT * FROM GithubIssue")
    suspend fun getGithubIssuesList() : List<GithubIssue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<GithubIssue>)
}