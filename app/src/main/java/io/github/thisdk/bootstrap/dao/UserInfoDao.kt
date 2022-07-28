package io.github.thisdk.bootstrap.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.thisdk.bootstrap.domain.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM userinfo")
    fun findAll2Flow(): Flow<List<UserInfo>>

    @Query("SELECT * FROM userinfo")
    suspend fun findAll(): List<UserInfo>

    @Query("SELECT * FROM userinfo WHERE name IN (:name)")
    suspend fun findByName(name: String): UserInfo

    @Insert
    suspend fun insert(user: UserInfo)

    @Delete
    suspend fun delete(user: UserInfo)

}
