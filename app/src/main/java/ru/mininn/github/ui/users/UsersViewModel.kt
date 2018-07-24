package ru.mininn.github.ui.users

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mininn.github.database.Database
import ru.mininn.github.model.GitUser
import ru.mininn.github.repository.UserSRepository
import ru.mininn.github.rest.GitApiClient

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val usersLiveData = MutableLiveData<List<GitUser>>()

    private val userRepository by lazy { UserSRepository(
            Database.databaseBuilder(application.applicationContext).allowMainThreadQueries().build().getUserDao(), GitApiClient.create()) }

    fun getUsers() : LiveData<List<GitUser>>{
        return usersLiveData
    }

    fun requestUsers() {
        userRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ usersLiveData.postValue(it) }, { it.printStackTrace() })
    }
}