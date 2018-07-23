package ru.mininn.github.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.mininn.github.R
import ru.mininn.github.model.GitUser
import ru.mininn.github.repository.UsersLiveData

class MainActivity : AppCompatActivity(), Observer<List<GitUser>> {
    override fun onChanged(t: List<GitUser>?) {
        t?.forEach { Log.d("asdasd", it.id.toString() + " " + it.login) }
    }

    val usersLiveData by lazy { UsersLiveData.getInstance(this.applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersLiveData.observeForever(this)
        this.text_view.setOnClickListener {
            usersLiveData.requestMoreUsers()
        }
    }

    override fun onStop() {
        super.onStop()
        usersLiveData.removeObserver(this)
    }
}
