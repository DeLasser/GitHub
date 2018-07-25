package ru.mininn.github.ui.userDetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_user_detail.*
import ru.mininn.github.Constants
import ru.mininn.github.R
import ru.mininn.github.model.GitUserProfile

class UserDetailsActivity : AppCompatActivity(), Observer<GitUserProfile> {
    private val userViewModel by lazy { ViewModelProviders.of(this).get(UserDetailViewModel::class.java) }

    private var name: TextView? = null
    private var email: TextView? = null
    private var company: TextView? = null
    private var bio: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initViews()
        userViewModel.userLiveData.observe(this, this)
        if (userViewModel.userLiveData.value != null) {
            setData(userViewModel.userLiveData.value!!)
        } else if (intent.getStringExtra(Constants.PROFILE_URL_KEY) != null) {
            userViewModel.requestUsers(intent.getStringExtra(Constants.PROFILE_URL_KEY))
        }
    }

    override fun onChanged(t: GitUserProfile?) {
        setData(t!!)
    }

    private fun initViews() {
        name = this.user_name
        email = this.user_email
        company = this.user_company
        bio = this.user_bio
    }

    private fun setData(userProfile: GitUserProfile) {
        name?.text = if (TextUtils.isEmpty(userProfile.name)) userProfile.login else userProfile.name
        email?.text = if (TextUtils.isEmpty(userProfile.email)) getString(R.string.email_not_found) else userProfile.email
        company?.text = if (TextUtils.isEmpty(userProfile.company)) getString(R.string.no_company) else userProfile.company
        bio?.text = if (TextUtils.isEmpty(userProfile.company)) getString(R.string.no_bio) else userProfile.bio
    }
}