@file:OptIn(ExperimentalPagingApi::class)

package io.ak1.userlist.ui.screens

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import io.ak1.userlist.data.repository.UserRepository

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */
class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val getAllUsers = userRepository.getAllUsers()
}