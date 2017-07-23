package com.example.demo.util.optionals

import java.util.*

fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)