package ru.mininn.github.ui.usersList.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_users_list.view.*
import ru.mininn.github.Constants
import ru.mininn.github.R
import ru.mininn.github.model.GitUser
import ru.mininn.github.ui.userDetail.UserDetailsActivity
import ru.mininn.github.ui.usersList.UsersViewModel
import ru.mininn.github.ui.usersList.adapter.UsersAdapter
import ru.mininn.github.util.Action

class UsersListFragment : Fragment(), Observer<List<GitUser>>, SwipeRefreshLayout.OnRefreshListener {
    private val RECYCLER_STATE = "recycler_state"
    private var recyclerView: RecyclerView? = null
    private var layout: SwipeRefreshLayout? = null
    private var adapter: UsersAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private val usersViewModel by lazy { ViewModelProviders.of(this).get(UsersViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_users_list, container, false)
        initViews(view)
        layout?.setOnRefreshListener(this)
        initAdapter()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        usersViewModel.getUsers().observe(this, this)
        if (usersViewModel.getUsers().value!!.isEmpty()) {
            usersViewModel.requestUsers(false)
        } else {
            initPagination()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(RECYCLER_STATE, layoutManager!!.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            layoutManager?.onRestoreInstanceState(savedInstanceState.getParcelable(RECYCLER_STATE))
        }
    }

    override fun onChanged(t: List<GitUser>?) {
        initPagination()
        adapter?.data = t
        adapter?.notifyDataSetChanged()
        layout?.isRefreshing = false
    }

    override fun onRefresh() {
        usersViewModel.requestUsers(true)
    }

    private fun initViews(view: View) {
        recyclerView = view.recycler_user_list
        layout = view.layout_user_list
    }

    private fun initAdapter() {
        adapter = UsersAdapter(usersViewModel.getUsers().value, Action {
            if (!TextUtils.isEmpty(it)) {
                openDetailsActivity(it!!)
            }
        })
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
    }

    private fun initPagination() {
        recyclerView?.clearOnScrollListeners()
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition: Int = layoutManager!!.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition >= (adapter!!.itemCount - 15)) {
                    usersViewModel.requestUsers(false)
                    recyclerView?.removeOnScrollListener(this)
                }
            }
        })
    }

    private fun openDetailsActivity(url: String) {
        val intent = Intent(context, UserDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.PROFILE_URL_KEY, url)
        intent.putExtra(Constants.PROFILE_URL_KEY, url)
        startActivity(intent)
    }

}
