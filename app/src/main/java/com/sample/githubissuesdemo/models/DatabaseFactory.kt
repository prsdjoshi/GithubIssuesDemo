package com.sample.githubissuesdemo.models

import android.content.Context
import androidx.room.Room
import com.sample.githubissuesdemo.models.database.AppDatabase

object DatabaseFactory {

    fun getDBInstance(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "GithubUserDb")
            .fallbackToDestructiveMigration()
            .build()
}