
关注公众号学习更多知识

![](https://files.mdnice.com/user/15648/404c2ab2-9a89-40cf-ba1c-02df017a4ae8.jpg)


# Compose可以配合多个Jetpack组件使用
`Compose`可以配合多个`Jetpack`组件开发提高开发效率

# 多种组合方式
## Compose配合ViewModel使用
### 概述
`Compose`中`ViewModel`的使用和`Jetpack`一致，通常我们构建页面的时候，如果一条数据多个布局都需要使用到的话我们只能在方法的形参中层层传递。但是这样明显是不合理的，会降低代码的可读性。

使用`ViewModel`可以完美的解决这个问题，`Compose`中使用`ViewModel`的话需要引入`lifecycle-viewmodel-compose`库，获取`ViewModel`的方式需要用到扩展函数：`viewModel()`

多个`@Composable`修饰的函数里面使用`viewModel()`获取`ViewModel`可以获取到同一个ViewModel对象，这就是我们能解决问题的根本原因

> 以上所说仅限于同一个导航页中。如果是在不同的导航页中，那么获取到的`ViewModel`是不同的对象，这一点跟我们不同`Activity`中获取不同`ViewModel`是一样的

### 依赖支持

```groovy
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07'
```
### 代码

```kotlin
@Composable
fun useWithViewModel() {
    val datas = remember {
        mutableStateListOf(
            ItemData(title = "向ViewModel中的值自增1并展示", content = "0")
        )
    }
    val model: ExampleViewModel = viewModel()//获取ViewModel对象
    Scaffold(topBar = {
        buildTopBar(title = "Compose和ViewModel的结合")
    }) {
        ListView(
            datas = datas,
            state = rememberLazyListState(),
            click = { itemData: ItemData, index, _ ->
                model.increase()//ViewModel中对象自增1
                itemData.content = model.count.toString()//刷新数据
                changeData(datas, index)
            })
    }
}
```
ExampleViewModel对象


```kotlin
class ExampleViewModel: ViewModel() {
    fun increase() {
        count++
    }

    var count =0
}
```


## 数据流Flow
### 概述
Compose可以在不导入依赖的情况下使用Flow，用法基本与相同。不过`Compose`中使用`StateFlow`不需要我们在协程中开启`collect`收集数据流，使用的时候直接使用`Flow.collectAsState`即可获取到StateFlow中的值进行展示。

### 示例代码
下面代码使用了`MutableStateFlow`实现了数据监听，当更新`MutableStateFlow`值的时候函数会被刷新，然后使用`collectAsState`即可获取到最新值进行展示。

点击下面的按钮改变值，上面的按钮内容被改变展示效果
```kotlin

@Composable
fun useStateFlow() {
    var repository = remember {
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
```
### 运行效果

![](https://files.mdnice.com/user/15648/3fe9aab1-426c-475f-82e0-3a6ce3172527.gif)


## Hilt
初学者可以将`Hilt`的学习延后，`Hilt`不是学习`Compose`的充要条件

hilt的使用和传统开发基本一致，可以查看我的另一篇[文章](https://juejin.cn/post/6967148539277213733)：https://juejin.cn/post/6967148539277213733


## coil
coil是一个图片库，可以用来加载Compose中的远程图片
### 依赖

```groovy
implementation 'io.coil-kt:coil-compose:1.3.2'
```
### 代码


```kotlin
@Composable
fun useCoil() {
    val painter =
        rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F27%2F20150127103509_KvXhU.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631501719&t=9653a6a5bb4e29505b9b582c770b42ef",
            builder = {
                crossfade(true)
            })
    Image(
        modifier = Modifier
            .size(300.dp)
            .clip(shape = RoundedCornerShape(20.dp)),
        painter = painter,
        contentDescription = ""
    )
}
```

### 各种效果

#### 圆形效果展示

```kotlin
 rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F27%2F20150127103509_KvXhU.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631501719&t=9653a6a5bb4e29505b9b582c770b42ef",
            builder = {
                transformations(CircleCropTransformation())
            })
```

![](https://files.mdnice.com/user/15648/7e15ef63-5f4b-4a55-8d39-2c1384122cef.png)
#### 圆角效果

```kotlin
  rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F27%2F20150127103509_KvXhU.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631501719&t=9653a6a5bb4e29505b9b582c770b42ef",
            builder = {
                transformations(
                    RoundedCornersTransformation()
                )
            })
```

![](https://files.mdnice.com/user/15648/ceef1cdc-c54f-439d-9508-e229cdec9489.png)
#### 圆形加灰色效果

```kotlin
  val painter =
        rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F27%2F20150127103509_KvXhU.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631501719&t=9653a6a5bb4e29505b9b582c770b42ef",
            builder = {
                transformations(
                    listOf(GrayscaleTransformation(), CircleCropTransformation())
                )
            })
```

![](https://files.mdnice.com/user/15648/581d8115-e8a1-434d-8e4b-778e1a405aed.png)



> 关注我的公众号 “安安安安卓” 学习更多知识

