package com.sample.githubissuesdemo.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
data class GithubIssue(
    @SerializedName("url")
    var url: String,

    @SerializedName("repository_url")
    var repositoryUrl: String,

    @SerializedName("labels_url")
    var labelsUrl: String,

    @SerializedName("comments_url")
    var commentsUrl: String,

    @SerializedName("events_url")
    var eventsUrl: String,

    @SerializedName("html_url")
    var htmlUrl: String,

    @SerializedName("id") @PrimaryKey
    var id: Int,

    @SerializedName("node_id")
    var nodeId: String,

    @SerializedName("number")
    var number: Int,

    @SerializedName("title")
    var title: String?,

    @TypeConverters(Converters::class)
    @Embedded
    @SerializedName("user")
    var user: ItemIssue,

    @SerializedName("created_at")
    var createdAt: String,

    @SerializedName("updated_at")
    var updatedAt: String,

    @SerializedName("body")
    var body: String?
    )