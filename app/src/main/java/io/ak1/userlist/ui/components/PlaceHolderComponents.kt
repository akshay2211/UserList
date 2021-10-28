package io.ak1.userlist.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.shimmer.ShimmerFrameLayout
import io.ak1.userlist.R

/**
 * Created by akshay on 28/10/21
 * https://ak1.io
 */


@Composable
fun PlaceHolder(
    @DrawableRes imageId: Int? = null,
    @StringRes titleId: Int? = null,
    @StringRes descriptionId: Int? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        imageId?.let {
            Image(
                painter = painterResource(it),
                contentDescription = stringResource(R.string.placeholder_image_desc),
                modifier = Modifier
                    .padding(12.dp)
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        titleId?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(0.dp, 9.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        descriptionId?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Shimmer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val shimmer = remember {
        ShimmerFrameLayout(context).apply {
            addView(ComposeView(context).apply {
                setContent(content)
            })
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { shimmer }
    ) { it.startShimmer() }
}

@Composable
fun ShimmerPlaceholder() {
    Shimmer {

        Row(
            modifier = Modifier
                .padding(start = 32.dp, top = 16.dp, end = 32.dp)
                .fillMaxWidth()
                .height(88.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "",
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        colorResource(id = android.R.color.darker_gray),
                        shape = CircleShape
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1f, fill = true),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(0.dp, 2.dp)
                        .background(
                            colorResource(id = android.R.color.darker_gray),
                            shape = RoundedCornerShape(5.dp)
                        ),
                    text = ""
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(0.dp, 2.dp)
                        .background(
                            colorResource(id = android.R.color.darker_gray),
                            shape = RoundedCornerShape(5.dp)
                        ),
                    text = ""
                )
            }
        }
    }
}