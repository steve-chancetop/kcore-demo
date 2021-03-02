package app.demo

import app.demo.service.TestInterface
import app.demo.service.TestService
import com.wonder.api.ApiSettings
import com.wonder.api.ServiceRegistry.service
import com.wonder.api.plugins.HealthCheckPlugin
import com.wonder.api.plugins.OpenAPIPlugin
import com.wonder.core.Configuration
import com.wonder.core.logging.Logging
import com.wonder.core.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

@ExperimentalCoroutinesApi
fun main() {
    Configuration.initialize()
    Logging.configure(level = Configuration.tryGetLogLevel())

    try {
        runBlocking(Dispatchers.Default) {
            launch {
                val settings = ApiSettings(
                    enableRequestLogging = true,
                    enableMetrics = true,
                    enableCoreNgJsonSerializer = true
                )

                settings.build {
                    registerPlugin(OpenAPIPlugin(settings, "1.0"))
                    registerPlugin(HealthCheckPlugin { Result.Companion.of("OK") })
                }
                    .service(TestInterface::class.java, TestService::class.java)
                    .start(Configuration.getValue("PORT", 8080))
            }
        }
    } catch (ex: Exception) {
        Logging.coreNgLogger.fatal(ex)
        exitProcess(1)
    }
}
