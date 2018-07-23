package ru.mininn.github.rest

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mininn.github.Constants

class GitApiClient {
    companion object {
        fun create(): GitApi {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.BASE_GIT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GitApi::class.java)
        }
    }
}