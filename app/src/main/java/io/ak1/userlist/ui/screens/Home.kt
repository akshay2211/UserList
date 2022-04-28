package io.ak1.userlist.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import io.ak1.userlist.R
import io.ak1.userlist.models.User
import io.ak1.userlist.ui.components.PlaceHolder
import io.ak1.userlist.ui.components.ShimmerPlaceholder
import kotlinx.coroutines.flow.Flow

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */

@Composable
fun HomeScreenComposable(
    userViewModel: UserViewModel,
    navController: NavController
) {
    UserList(users = userViewModel.getAllUsers, navController)
}

@Composable
fun UserList(users: Flow<PagingData<User>>, navController: NavController) {
    val lazyUserItems = users.collectAsLazyPagingItems()
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                Row {
                    Spacer(modifier = Modifier.weight(weight = 1f, fill = true))
                    Image(
                        painter = painterResource(id = R.drawable.ic_settings),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                        contentDescription = "Header Image",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(12.dp)
                            .clickable {
                                navController.navigate(Destinations.SETTINGS_ROUTE)
                            }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_undraw_people),
                    contentDescription = "Header Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
                Text(
                    modifier = Modifier.padding(16.dp, 2.dp),
                    text = stringResource(id = R.string.heading),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier.padding(16.dp, 2.dp),
                    text = stringResource(id = R.string.subtitle),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start,
                )
            }

        }


        items(lazyUserItems) { user ->
            user?.let {
                UserItem(user = it)
            }
        }

        lazyUserItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        PlaceHolder(
                            imageId = R.drawable.ic_undraw_loading,
                            descriptionId = R.string.loading
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item { ShimmerPlaceholder() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyUserItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyUserItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {

    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(88.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(
                data = user.avatar,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = "avatar Image",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f, fill = true),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.padding(0.dp, 2.dp),
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.padding(0.dp, 2.dp),
                text = user.email ?: "",
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Start,
            )
        }
    }
    /* Divider(modifier = Modifier
         .height(0.5.dp)
         .fillMaxWidth()
         .background(colorResource(id = android.R.color.darker_gray)))*/
}



@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}