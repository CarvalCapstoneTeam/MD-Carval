package com.dicoding.carvalappandroid.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
class RemoteKeys (
    @PrimaryKey
    val id : String,
    val prevKey : Int?,
    val nextKey : Int?
)