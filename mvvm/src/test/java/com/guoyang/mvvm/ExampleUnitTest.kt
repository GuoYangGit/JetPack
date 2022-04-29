package com.guoyang.mvvm

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val date = Date(1645759013208)
        // 12小时制
        println(SimpleDateFormat("hh:mm a", Locale.CHINESE).format(date))
        // 星期
        println(SimpleDateFormat("E", Locale.ENGLISH).format(date))
        println(SimpleDateFormat("EEEE hh:mm a", Locale.ENGLISH).format(date))
        // 月份
        println(SimpleDateFormat("MMM d", Locale.ENGLISH).format(date))
        println(SimpleDateFormat("MMMM d hh:mm a", Locale.ENGLISH).format(date))
        // 年
        println(SimpleDateFormat("yy/M/d", Locale.ENGLISH).format(date))
        println(SimpleDateFormat("yy/M/d hh:mm a", Locale.ENGLISH).format(date))


        println("${80 / 0.00062137F}")
    }
}