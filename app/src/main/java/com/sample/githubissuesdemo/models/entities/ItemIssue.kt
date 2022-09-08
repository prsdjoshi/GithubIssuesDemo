package com.sample.githubissuesdemo.models.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ItemIssue(

    @SerializedName("login")
    var login: String ?,

    @SerializedName("id")
    var userId: Int,

    @SerializedName("node_id")
    var userNodeId: String,

    @SerializedName("avatar_url")
    var avatarUrl: String ?,

    @SerializedName("gravatar_id")
    var gravatarId: String,

    @SerializedName("url")
    var userUrl: String,

    @SerializedName("html_url")
    var userHtmlUrl: String,

    @SerializedName("followers_url")
    var followersUrl: String,

    @SerializedName("following_url")
    var followingUrl: String,

    @SerializedName("gists_url")
    var gistsUrl: String,

    @SerializedName("starred_url")
    var starredUrl: String,

    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String,

    @SerializedName("organizations_url")
    var organizationsUrl: String,

    @SerializedName("repos_url")
    var reposUrl: String,

    @SerializedName("events_url")
    var userEventsUrl: String,

    @SerializedName("received_events_url")
    var receivedEventsUrl: String,

    @SerializedName("type")
    var type: String,

    @SerializedName("site_admin")
    var siteAdmin: Boolean
)