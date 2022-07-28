package io.github.thisdk.bootstrap.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.thisdk.bootstrap.dao.UserInfoDao
import io.github.thisdk.bootstrap.domain.UserInfo

@Database(entities = [UserInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
}