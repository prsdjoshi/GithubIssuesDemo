package com.sample.githubissuesdemo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sample.githubissuesdemo.R
import com.sample.githubissuesdemo.models.entities.Follower
import com.sample.githubissuesdemo.models.entities.GithubIssue

class FollowerListAdapter(
    private val onItemClick: (Follower) -> Unit
) : PagingDataAdapter<Follower, FollowerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_github_issue, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onItemClick)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Follower>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldUser: Follower,
                                         newUser: Follower) = oldUser.login == newUser.login

            override fun areContentsTheSame(oldUser: Follower,
                                            newUser: Follower) = oldUser == newUser
        }
    }
}