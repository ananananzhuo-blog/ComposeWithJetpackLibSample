package com.ananananzhuo.composewithjetpacklibsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ananananzhuo.composelib.ListView
import com.ananananzhuo.composelib.bean.ItemData
import com.ananananzhuo.composelib.buildTopBar
import com.ananananzhuo.composelib.changeData
import com.ananananzhuo.composelib.constants.PAGE1
import com.ananananzhuo.composelib.constants.PAGE2
import com.ananananzhuo.composelib.constants.PAGE3
import com.ananananzhuo.composelib.statusbarColor
import com.ananananzhuo.composewithjetpacklibsample.ui.theme.ComposeWithJetpackLibSampleTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val HOME = "home"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWithJetpackLibSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    rememberSystemUiController().run {
        setStatusBarColor(Color.Transparent, darkIcons = false)
        setSystemBarsColor(statusbarColor, darkIcons = false)
        setNavigationBarColor(statusbarColor, darkIcons = false)
    }
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = HOME) {
        composable(HOME) {//首页
            Home(controller = controller)
        }
        composable(PAGE1) {
            useWithViewModel()
        }
        composable(PAGE2){
            useStateFlow()
        }
        composable(PAGE3){
            useCoil()
        }
    }
}


@Composable
fun Home(controller: NavController) {
    val model: ExampleViewModel = viewModel()
    val datas = remember {
        mutableStateListOf(
            ItemData(title = "Compose中使用ViewModel", tag = PAGE1),
            ItemData(title = "首页和导航后跳转的页面同时获取ViewModel"),
            ItemData(title = "Compose中使用StateFlow",tag = PAGE2),
            ItemData(title = "使用coil加载网络图片",tag = PAGE3)
        )
    }

    Scaffold(topBar = {
        buildTopBar(title = "Compose中使用Jetpack组件")
    }) {
        ListView(
            datas = datas,
            click = { itemData: ItemData, index, _ ->
                when (index) {
                    1 -> {
                        model.increase()
                        itemData.content = model.count.toString()
                        changeData(datas, index)
                    }
                    else -> {
                        if (itemData.tag.isNotEmpty()) {
                            controller.navigate(itemData.tag)
                        }
                    }
                }
            },
            state = rememberLazyListState()
        )
    }

}