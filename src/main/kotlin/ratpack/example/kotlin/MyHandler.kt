package ratpack.example.kotlin

import javax.inject.Inject
import javax.inject.Singleton
import ratpack.core.handling.Context
import ratpack.core.handling.Handler

/**
 * A handler implementation that is created via dependency injection.
 *
 * @see MyModule
 */
@Singleton class MyHandler @Inject constructor(val myService: MyService) : Handler {
    override fun handle(context: Context) =
        context.response.send("service value: ${myService.getValue()}")
}
