package com.sample.githubissuesdemo.models.services

import com.sample.githubissuesdemo.models.entities.Follower
import com.sample.githubissuesdemo.models.entities.GithubIssue
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubIssuesServices {

    @GET("repos/square/okhttp/issues")
    suspend fun githubIssueList(): List<GithubIssue>

    @GET("repos/square/okhttp/issues/{id}")
    suspend fun getIssueDetails(@Path("id") userName: String): GithubIssue

    @GET("repos/square/okhttp/issues/{id}/comments")
    suspend fun getCommentsList(
        @Path("id") userName: String): List<GithubIssue>

    @GET("/users/{username}/followers")
    suspend fun getFollowersList(
        @Path("username") userName: String?): List<Follower>
}