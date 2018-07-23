package ru.mininn.github.repository

import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.mininn.github.database.GitUserDao
import ru.mininn.github.model.GitUser
import ru.mininn.github.rest.GitApi

class UserRepository(private val dao: GitUserDao, private val apiClient: GitApi) {
    private var lastUserId: Int = 0
    private var cachedUsers = ArrayList<GitUser>()

    fun getUsers(): Observable<List<GitUser>> {
        return getUsersFromApi().onErrorResumeNext(getUsersFromDb())

    }

    fun getMoreUsers(): Observable<List<GitUser>> {
        return getUsersFromApi()
    }

    private fun getUsersFromApi(): Observable<List<GitUser>> {
        return apiClient.getUsers(lastUserId)
                .doOnNext {
                    lastUserId = it[it.lastIndex].id
                    saveToDb(it)
                }
    }


    private fun getUsersFromDb(): Observable<List<GitUser>> {
        return dao.getAllUsers()
                .toObservable()
    }

    private fun saveToDb(users: List<GitUser>) {
        Observable
                .fromCallable { dao.insertAllUsers(users) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
    }

    private fun clearDbFromId(id: Int) {
        dao.delleteFromId(id)
    }
}