package ru.mininn.github.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.mininn.github.R
import ru.mininn.github.model.GitUser
import ru.mininn.github.ui.users.UsersViewModel

class MainActivity : AppCompatActivity(), Observer<List<GitUser>> {

    private val usersViewModel by lazy { ViewModelProviders.of(this).get(UsersViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersViewModel.getUsers().observe(this,this)
        usersViewModel.getUsers().value?.forEach { Log.d("asdasd", it.id.toString() + " " + it.login) }

        this.text_view.setOnClickListener {
            usersViewModel.requestUsers()
        }
    }

    override fun onChanged(t: List<GitUser>?) {
        t?.forEach { Log.d("asdasd", it.id.toString() + " " + it.login) }
    }
}
