package io.ak1.userlist.ui.screens

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import io.ak1.userlist.data.repository.UserRepository
import io.ak1.userlist.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */
class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users: Flow<PagingData<User>> = userRepository.pager.flow
}