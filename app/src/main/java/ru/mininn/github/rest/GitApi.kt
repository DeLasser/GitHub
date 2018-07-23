package ru.mininn.github.rest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mininn.github.model.GitUser

interface GitApi {

    @GET("users")
    fun getUsers(@Query("since") user : Int):
            Observable<List<GitUser>>
}