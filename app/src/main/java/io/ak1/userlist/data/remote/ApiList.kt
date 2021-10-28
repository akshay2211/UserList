package io.ak1.userlist.data.remote

import io.ak1.userlist.models.BaseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */
interface ApiList {

    //https://reqres.in/api/users?page=1&delay=3

    @GET("users?delay=3")
    suspend fun getUserList(
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 3
    ): Response<BaseData>

    companion object {
        const val BASE_PATH = "https://reqres.in/api/"
    }
}