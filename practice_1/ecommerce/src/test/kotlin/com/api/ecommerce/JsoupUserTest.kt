package com.api.ecommerce

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException

@RunWith(SpringRunner::class)
@SpringBootTest
class JsoupUserTest {

    private val _log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    @Throws(IOException::class)
    fun testUsersJsoup() {
//        val doc: String = Jsoup.connect("http://localhost:8080/user").ignoreContentType(true).get().body().text()
//        _log.info("{test} doc : $doc")
//        val parser = JsonParser()
//        val userElement: JsonElement = parser.parse(doc)
//        val userArray: JsonArray = userElement.asJsonArray
//        _log.info("{test} size : " + userArray.size())
//        assertEquals(4, userArray.size())
    }
}