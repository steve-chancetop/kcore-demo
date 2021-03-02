package app.demo.service

import com.wonder.api.Service
import io.javalin.http.Context

/**
 * @author steve
 */
class TestService(override val context: Context) : Service, TestInterface {
    override fun test(): TestResponse {
        val testResponse = TestResponse()
        testResponse.f1 = "hello, world!"
        return testResponse
    }
}
