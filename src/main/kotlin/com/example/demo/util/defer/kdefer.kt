package com.example.demo.util.defer

import java.io.Closeable
import java.util.concurrent.ConcurrentLinkedDeque

/*
  see: https://gobyexample.com/defer
 */


private typealias DeferredAction = () -> Any?

class Defer : Closeable, AutoCloseable {

    private val stack = ConcurrentLinkedDeque<DeferredAction>()

    fun add(action: DeferredAction) = push(action)
    fun addGraceful(action: DeferredAction) {
        push({
            try {
                action.invoke()
            } catch (ignore: Throwable) {
            }
        })
    }

    override fun close() {
        while (stack.isNotEmpty()) {
            pop()?.invoke()
        }
    }

    private fun push(action: DeferredAction) = stack.push(action)

    private fun pop(): DeferredAction? {
        return try {
            if (stack.isNotEmpty()) stack.pop() else null
        } catch (ignore: Throwable) {
            null
        }
    }

}
