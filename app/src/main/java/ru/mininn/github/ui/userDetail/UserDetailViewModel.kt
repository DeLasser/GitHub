package ru.mininn.github.ui.userDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mininn.github.database.Database
import ru.mininn.github.model.GitUserProfile
import ru.mininn.github.repository.UsersRepository
import ru.mininn.github.rest.GitApiClient

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    val userLiveData = MutableLiveData<GitUserProfile>()

    private val userRepository by lazy {
        UsersRepository(
                Database.databaseBuilder(application.applicationContext).allowMainThreadQueries().build().getUserDao(), GitApiClient.create())
    }

    fun requestUsers(url: String) {
        userRepository.getUserDetails(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userLiveData.postValue(it) }, { it.printStackTrace() })
    }
}