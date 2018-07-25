package ru.mininn.github.rest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.mininn.github.model.GitUser
import ru.mininn.github.model.GitUserProfile

interface GitApi {

    @GET("users")
    fun getUsers(@Query("since") user: Int): Observable<List<GitUser>>

    @GET
    fun getUserInfo(@Url url: String): Observable<GitUserProfile>
}