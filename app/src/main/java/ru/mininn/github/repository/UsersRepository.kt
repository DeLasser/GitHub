package ru.mininn.github.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import ru.mininn.github.database.GitUserDao
import ru.mininn.github.model.GitUser
import ru.mininn.github.rest.GitApi

class UsersRepository(private val dao: GitUserDao, private val apiClient: GitApi) {
    private var lastUserId: Int = 0
    private var cachedUsers = Observable.fromArray(ArrayList<GitUser>())

    fun getUsers(refresh: Boolean): Observable<List<GitUser>> {
        if (refresh) {
            lastUserId = 0
        }
        return Observable.combineLatest(cachedUsers, getUsersFromApi(),
                BiFunction<ArrayList<GitUser>, List<GitUser>, List<GitUser>> { cached, rest ->
                    if (refresh) {
                        cached.clear()
                    }
                    cached.addAll(rest)
                    cached
                }).onErrorResumeNext(getUsersFromDb())
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
                .toObservable().doOnNext {
                    lastUserId = it[it.lastIndex].id
                }
    }

    private fun saveToDb(users: List<GitUser>) {
        Observable
                .fromCallable { dao.insertAllUsers(users) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
    }
}