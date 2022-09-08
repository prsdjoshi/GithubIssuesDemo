package com.sample.githubissuesdemo.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sample.githubissuesdemo.R
import com.sample.githubissuesdemo.models.entities.GithubIssue

class CommentsListAdapter(
    private val onItemClick: (GithubIssue) -> Unit
) : PagingDataAdapter<GithubIssue, ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_github_issue, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onItemClick)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<GithubIssue>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldUser: GithubIssue,
                                         newUser: GithubIssue) = oldUser.user.login == newUser.user.login

            override fun areContentsTheSame(oldUser: GithubIssue,
                                            newUser: GithubIssue) = oldUser == newUser
        }
    }
}