package io.ak1.userlist.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */
@Parcelize
@Entity(tableName = "user_table", indices = [Index(value = ["email"], unique = true)])
data class User(
    var email: String?,
    @SerializedName("first_name") var firstName: String?,
    @SerializedName("last_name") var lastName: String?,
    var avatar: String?,
) : Parcelable {
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("page_num")
    var pageNum: Int = 0

    @SerializedName("total_page")
    var totalPage: Int = 0
}

@Parcelize
@Entity(tableName = "remote_keys")
data class RemoteKey(@PrimaryKey val label: String, val nextKey: String?) : Parcelable
