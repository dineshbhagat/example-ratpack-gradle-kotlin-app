@file:Suppress("Unused")

/*
 * Test helpers.
 */

package ratpack.example.kotlin

import ratpack.core.server.RatpackServer
import ratpack.test.embed.EmbeddedApp
import ratpack.test.embed.EmbeddedApp.*
import ratpack.test.http.TestHttpClient

fun appFromHandler(callback: KContext.() -> Unit): EmbeddedApp =
    fromHandler { KContext(it).(callback) () }
fun appFromHandlers(callback: KChain.() -> Unit): EmbeddedApp =
    fromHandlers { KChain(it).(callback) () }
fun appFromServer(startServer: RatpackServer) = fromServer (startServer)
fun EmbeddedApp.check(callback: TestHttpClient.() -> Unit): Unit = this.test { it.(callback)() }
fun TestHttpClient.getBody(path: String) = get(path).body.text
