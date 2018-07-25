package ru.mininn.github.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class GitUser(@PrimaryKey @Expose @SerializedName("id")
              var id: Int,
              @Expose @SerializedName("login")
              var login: String,
              @Expose @SerializedName("url")
              var url: String,
              @Expose @SerializedName("avatar_url")
              var avatarUrl: String)
