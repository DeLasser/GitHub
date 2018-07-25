package ru.mininn.github.ui.usersList.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.mininn.github.R
import ru.mininn.github.model.GitUser
import ru.mininn.github.util.Action

class UsersAdapter(var data: List<GitUser>?, var onItemClickAction: Action<String?>) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data!![position])
        holder.setOnClicListener(View.OnClickListener {
            onItemClickAction.execute(data!![position].url)
        })
    }
}