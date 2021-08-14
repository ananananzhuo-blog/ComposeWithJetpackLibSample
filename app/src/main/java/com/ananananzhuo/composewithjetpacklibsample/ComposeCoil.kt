package com.ananananzhuo.composewithjetpacklibsample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation

/**
 * author  :mayong
 * function:
 * date    :2021/8/14
 **/


@Composable
fun useCoil() {
    val painter =
        rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F27%2F20150127103509_KvXhU.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631501719&t=9653a6a5bb4e29505b9b582c770b42ef",
            builder = {
                transformations(
                    listOf(GrayscaleTransformation(), CircleCropTransformation())
                )
            })
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            painter = painter,
            contentDescription = ""
        )


    }

}