package ru.mininn.github.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.mininn.github.Constants
import ru.mininn.github.model.GitUser

@android.arch.persistence.room.Database(entities = [(GitUser::class)], version = 1)
abstract class Database : RoomDatabase() {
    companion object {
        fun databaseBuilder(context: Context): Builder<Database> {
            return Room.databaseBuilder(context, Database::class.java, Constants.DATABASE_NAME)
        }
    }

    abstract fun getUserDao(): GitUserDao
}