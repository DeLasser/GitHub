package ru.mininn.github.ui.users

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Parcelable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mininn.github.database.Database
import ru.mininn.github.model.GitUser
import ru.mininn.github.repository.UsersRepository
import ru.mininn.github.rest.GitApiClient

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val usersLiveData = MutableLiveData<List<GitUser>>()
    private var scrollState : Int = 0

    private val userRepository by lazy { UsersRepository(
            Database.databaseBuilder(application.applicationContext).allowMainThreadQueries().build().getUserDao(), GitApiClient.create()) }

    fun getUsers() : LiveData<List<GitUser>>{
        if (usersLiveData.value == null){
            usersLiveData.value = ArrayList<GitUser>()
        }
        return usersLiveData
    }

    fun requestUsers(refresh : Boolean) {
        userRepository.getUsers(refresh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ usersLiveData.postValue(it) }, { it.printStackTrace() })
    }

    fun saveScrollInstance(scrollState : Int) {
        this.scrollState = scrollState
    }

    fun getScrollState() : Int {
        return scrollState
    }

}