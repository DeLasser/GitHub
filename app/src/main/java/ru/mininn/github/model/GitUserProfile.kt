package ru.mininn.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GitUserProfile(@Expose @SerializedName("name")
                     var name: String?,
                     @Expose @SerializedName("login")
                     var login: String,
                     @Expose @SerializedName("email")
                     var email: String?,
                     @Expose @SerializedName("bio")
                     var bio: String?,
                     @Expose @SerializedName("company")
                     var company: String?) {
}