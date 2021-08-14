package com.ananananzhuo.composewithjetpacklibsample

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.ananananzhuo.composelib.bean.ItemData
import com.ananananzhuo.composelib.listItem
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * author  :mayong
 * function:
 * date    :2021/8/14
 **/


@Composable
fun useStateFlow() {
    val repository = remember {
        Repository()
    }
    Column {
        listItem(
            itemData = ItemData(
                title = "点击更改StateFlow的值",
                content = repository.stateFlow.collectAsState().value//获取StateFlow中的值展示
            ), onClick = {
            })
        changeUiWithState(repository)
    }

}

@Composable
fun changeUiWithState(repository: Repository) {
    listItem(itemData = ItemData(title = "点击改变数据"), onClick = {
        repository.increase()//点击数值自增1
    })
}

class Repository {
    val stateFlow = MutableStateFlow("初始值0")
    var count = 0
    fun increase(): Int {
        stateFlow.value = count.toString()//更改StateFlow中的值
        return count++
    }
}