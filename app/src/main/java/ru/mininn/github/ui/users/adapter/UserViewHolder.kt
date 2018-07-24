package ru.mininn.github.ui.users.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import ru.mininn.github.model.GitUser

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: GitUser) {
        Picasso.get().load(user.avatarUrl).into(itemView.user_image)
        itemView.user_login.text = user.login
    }
}