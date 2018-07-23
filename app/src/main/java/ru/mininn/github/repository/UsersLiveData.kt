package ru.mininn.github.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mininn.github.database.Database
import ru.mininn.github.model.GitUser
import ru.mininn.github.rest.GitApiClient

class UsersLiveData(context : Context) : LiveData<List<GitUser>>() {
    private val userRepository by lazy { UserRepository(Database.databaseBuilder(context).allowMainThreadQueries().build().getUserDao(), GitApiClient.create()) }

    private object Holder {
        var INSTANCE: UsersLiveData? = null
    }

    companion object {
        fun getInstance(context: Context) : UsersLiveData{
            if (Holder.INSTANCE == null) {
                Holder.INSTANCE = UsersLiveData(context)
            }
            return Holder.INSTANCE!!
        }
    }

    override fun onActive() {
        super.onActive()
        userRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({postValue(it)},{it.printStackTrace()})

    }

    fun requestMoreUsers() {
        userRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({postValue(it)},{it.printStackTrace()})
    }
}