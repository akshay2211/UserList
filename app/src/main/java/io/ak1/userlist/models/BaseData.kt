package io.ak1.userlist.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */

@Parcelize
class BaseData : Parcelable {
    var page: Int = 0

    @SerializedName("per_page")
    var perPage: Int = 0
    var total: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0
    var data: ArrayList<User> = ArrayList()
}