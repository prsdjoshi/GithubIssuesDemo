package com.sample.githubissuesdemo.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.githubissuesdemo.models.database.daos.GithubIssuesDao
import com.sample.githubissuesdemo.models.entities.GithubIssue

@Database(entities = [GithubIssue::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val githubIssuesDao: GithubIssuesDao
}