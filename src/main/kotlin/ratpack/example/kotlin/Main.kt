package ratpack.example.kotlin

import kotlin.system.exitProcess
import org.slf4j.LoggerFactory.getLogger
import ratpack.core.handling.Context
import ratpack.core.server.BaseDir

object Main {
    private val log = getLogger(Main::class.java)

    @JvmStatic fun main(args: Array<String>) {
        try {
            createServer().start()
        }
        catch (e: Exception) {
            log.error("", e)
            exitProcess(1)
        }
    }

    private fun createServer() = serverOf {
        serverConfig {
            baseDir(BaseDir.find())
        }

        guiceRegistry {
            module(MyModule())
        }

        handlers {
            // http://localhost:5050/foo
            path("foo") { render("from the foo handler") }
            // http://localhost:5050/bar
            path("bar") { render("from the bar handler") }

            // Map to /baz using a Kotlin function
            // http://localhost:5050/baz
            path("baz", ::bazHandler)

            // Set up a nested routing block, which is delegated to `nestedHandler`
            // http://localhost:5050/xx/yy, http://localhost:5050/xx
            prefix("nested") {
                path(":var1/:var2?") {
                    // The path tokens are the :var1 and :var2 path components above
                    val var1 = pathTokens["var1"]
                    val var2 = pathTokens["var2"]
                    render("from the nested handler, var1: $var1, var2: $var2")
                }
            }

            // Map to a dependency injected handler
            path("injected", MyHandler::class.java)

            // Bind the /static app path to the src/ratpack/assets/images dir
            //TODO, could not find this working, routes requests to nested/:var1/:var2? path
            prefix("static") {
                fileSystem("assets/images") { files() }
            }

            all { render("root handler!") }
        }
    }
}

/** A handler as a method */
fun bazHandler(context: Context) = context.render("from the baz handler")
