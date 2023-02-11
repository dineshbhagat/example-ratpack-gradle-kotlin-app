@file:Suppress("Unused")

/*
 * Helper functions to make the example more Kotlin like.
 */

package ratpack.example.kotlin

import ratpack.core.handling.Chain
import ratpack.core.handling.Context
import ratpack.core.server.RatpackServer
import ratpack.core.server.RatpackServerSpec
import ratpack.core.server.ServerConfigBuilder
import ratpack.exec.registry.Registry
import ratpack.exec.registry.RegistrySpec
import ratpack.guice.BindingsSpec
import ratpack.guice.Guice.registry as guiceRegistry

fun serverOf(cb: KServerSpec.() -> Unit): RatpackServer = RatpackServer.of { KServerSpec(it).cb() }
fun serverStart(cb: KServerSpec.() -> Unit): RatpackServer = RatpackServer.start { KServerSpec(it).cb() }

class KChain(private val delegate: Chain) : Chain by delegate {
    fun fileSystem(path: String = "", cb: KChain.() -> Unit): Chain =
        delegate.fileSystem(path) { KChain(it).cb() }

    fun prefix(path: String = "", cb: KChain.() -> Unit): Chain =
        delegate.prefix(path) { KChain(it).cb() }

    fun all(cb: Context.() -> Unit): Chain = delegate.all { it.cb() }
    fun path(path: String = "", cb: Context.() -> Unit): Chain = delegate.path(path) { it.cb() }

    @Suppress("ReplaceGetOrSet")
    fun get(path: String = "", cb: Context.() -> Unit): Chain = delegate.get(path) { it.cb() }
    fun put(path: String = "", cb: Context.() -> Unit): Chain = delegate.put(path) { it.cb() }
    fun post(path: String = "", cb: Context.() -> Unit): Chain = delegate.post(path) { it.cb() }
    fun delete(path: String = "", cb: Context.() -> Unit): Chain = delegate.delete(path) { it.cb() }
    fun options(path: String = "", cb: Context.() -> Unit): Chain = delegate.options(path) { it.cb() }
    fun patch(path: String = "", cb: Context.() -> Unit): Chain = delegate.patch(path) { it.cb() }
}

class KContext(private val delegate: Context) : Context by delegate

class KServerSpec(private val delegate: RatpackServerSpec) {
    fun serverConfig(cb: ServerConfigBuilder.() -> Unit): RatpackServerSpec = delegate.serverConfig { it.cb() }
    fun registry(cb: RegistrySpec.() -> Unit): RatpackServerSpec = delegate.registry(Registry.of(cb))
    fun guiceRegistry(cb: BindingsSpec.() -> Unit): RatpackServerSpec =
        delegate.registry(guiceRegistry { bindings: BindingsSpec -> bindings.cb() })

    fun handlers(cb: KChain.() -> Unit): RatpackServerSpec = delegate.handlers { KChain(it).cb() }
}
