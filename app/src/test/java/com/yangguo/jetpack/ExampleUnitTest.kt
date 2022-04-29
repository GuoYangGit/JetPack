package com.yangguo.jetpack

import org.junit.Test
import java.net.URLEncoder

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("İR")
        println(encodeHeadInfo("İR"))
        println(URLEncoder.encode("İR", "UTF-8"))
    }

    fun encodeHeadInfo(headInfo: String): String {
        val stringBuffer = StringBuffer()
        var i = 0
        val length: Int = headInfo.length
        while (i < length) {
            val c: Char = headInfo[i]
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", c.code))
            } else {
                stringBuffer.append(c)
            }
            i++
        }
        return stringBuffer.toString()
    }
}