package com.dicoding.carvalappandroid.remotekeys

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dicoding.carvalappandroid.api.APIService
import com.dicoding.carvalappandroid.data.JobDatabase
import com.dicoding.carvalappandroid.response.ArticleResponseItem

@OptIn(ExperimentalPagingApi::class)
class JobRemoteMediator(
    private val database : JobDatabase,
    private val apiService: APIService
) : RemoteMediator<Int, ArticleResponseItem>(){

    private suspend fun getRemoteKeyForLastItem(state : PagingState<Int, ArticleResponseItem>) : RemoteKeys?{
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data->
            database.remoteKeysDAO().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state : PagingState<Int, ArticleResponseItem>) : RemoteKeys?{
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data->
            database.remoteKeysDAO().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(state : PagingState<Int, ArticleResponseItem>) : RemoteKeys?{
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id->
                database.remoteKeysDAO().getRemoteKeysId(id)
            }
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleResponseItem>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH->{
                val remoteKeys = getRemoteKeyClosesToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1)?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND->{
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?:return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND->{
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?:return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        return try {
            val responseData = apiService.getArticlesUnlimited(page, state.config.pageSize).articleResponse
            val endOfPaginationReached = responseData!!.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    database.remoteKeysDAO().deleteRemoteKeys()
                    database.jobDAO().deleteAll()
                }
                val prevKey = if (page==1) null else page-1
                val nextKey = if (endOfPaginationReached) null else page+1
                val keys = responseData.map {
                    RemoteKeys(it?.id.toString(), prevKey, nextKey)
                }
                database.remoteKeysDAO().insertAll(keys)
                database.jobDAO().insertArticle(responseData)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (exception : Exception){
            MediatorResult.Error(exception)
        }
    }

}