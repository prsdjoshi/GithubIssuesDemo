package com.sample.githubissuesdemo

import com.sample.githubissuesdemo.models.RetrofitAPIFactory
import com.sample.githubissuesdemo.models.DatabaseFactory
import com.sample.githubissuesdemo.models.repositories.IGIssueDetailRepository
import com.sample.githubissuesdemo.models.repositories.IGithubIssueListRepository
import com.sample.githubissuesdemo.models.repositories.IssueDetailsRepository
import com.sample.githubissuesdemo.models.repositories.GithubIssueListRepository
import com.sample.githubissuesdemo.models.response.ResponseHandler
import com.sample.githubissuesdemo.util.ConnectionHelper
import com.sample.githubissuesdemo.viewmodel.IssueDetailViewModel
import com.sample.githubissuesdemo.viewmodel.IssueDetailViewModelFactory
import com.sample.githubissuesdemo.viewmodel.GithubIssueListViewModel
import com.sample.githubissuesdemo.viewmodel.GithubIssueListViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gitHubModuleInfo = module {
    single { RetrofitAPIFactory.retrofitAPI() }
    single { DatabaseFactory.getDBInstance(androidContext()) }

    single<IGithubIssueListRepository> { GithubIssueListRepository(get(), get()) }
    single { GithubIssueListViewModelFactory(get()) }
    single<IGIssueDetailRepository> { IssueDetailsRepository(get(), get()) }
    single { IssueDetailViewModelFactory(get()) }
    single { RetrofitAPIFactory }
    single { DatabaseFactory }
    single { ResponseHandler() }
    single { ConnectionHelper(androidContext()) }

    viewModel { GithubIssueListViewModel(get()) }
    viewModel { IssueDetailViewModel(get()) }

}