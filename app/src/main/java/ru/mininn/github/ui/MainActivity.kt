package ru.mininn.github.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import ru.mininn.github.R
import ru.mininn.github.ui.usersList.fragment.UsersListFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val usersListFragment by lazy { UsersListFragment() }
    private val buildInfoFragment by lazy { BuildInfoFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMenu()
        if (savedInstanceState == null) {
            replaceFragment(usersListFragment)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_users -> replaceFragment(usersListFragment)
            R.id.action_build -> replaceFragment(buildInfoFragment)
        }
        return true
    }

    private fun initMenu() {
        this.navigation_bottom_bar.inflateMenu(R.menu.menu_bottom_navigation)
        this.navigation_bottom_bar.setOnNavigationItemSelectedListener(this)
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }


}

