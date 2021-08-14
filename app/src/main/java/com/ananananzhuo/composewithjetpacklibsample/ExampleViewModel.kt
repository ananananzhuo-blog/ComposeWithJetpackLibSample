package com.ananananzhuo.composewithjetpacklibsample

import androidx.lifecycle.ViewModel

/**
 * author  :mayong
 * function:
 * date    :2021/8/13
 **/
class ExampleViewModel: ViewModel() {
    fun increase() {
        count++
    }

    var count =0
}