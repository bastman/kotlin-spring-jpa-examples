package com.example.demo.util.fp

// see: https://github.com/MarioAriasC/funKTionale/blob/master/funktionale-pipe/src/main/kotlin/org/funktionale/pipe/pipe.kt
infix inline fun <T, R> T.pipe(t: (T) -> R): R = t(this)
