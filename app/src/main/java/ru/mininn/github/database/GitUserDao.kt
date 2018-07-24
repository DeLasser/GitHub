package ru.mininn.github.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import ru.mininn.github.model.GitUser
@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<GitUser>)

    @Query("SELECT * FROM GitUser")
    fun getAllUsers(): Single<List<GitUser>>

}