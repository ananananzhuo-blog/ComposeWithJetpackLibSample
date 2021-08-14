package com.ananananzhuo.composewithjetpacklibsample

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ananananzhuo.composelib.ListView
import com.ananananzhuo.composelib.bean.ItemData
import com.ananananzhuo.composelib.buildTopBar
import com.ananananzhuo.composelib.changeData

/**
 * author  :mayong
 * function:
 * date    :2021/8/13
 **/

@Composable
fun useWithViewModel() {
    val datas = remember {
        mutableStateListOf(
            ItemData(title = "向ViewModel中的值自增1并展示", content = "0")
        )
    }
    val model: ExampleViewModel = viewModel()
    Scaffold(topBar = {
        buildTopBar(title = "Compose和ViewModel的结合")
    }) {
        ListView(
            datas = datas,
            state = rememberLazyListState(),
            click = { itemData: ItemData, index, _ ->
                model.increase()
                itemData.content = model.count.toString()
                changeData(datas, index)
            })
    }
}