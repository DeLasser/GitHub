package ru.mininn.github.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.mininn.github.R
import ru.mininn.github.ui.users.fragment.UsersListFragment

class MainActivity : AppCompatActivity() {
    private val FRAGMENT_TAG_USERS = "users_list"
    private val usersListFragment by lazy { UsersListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,usersListFragment).commit();
    }
}

