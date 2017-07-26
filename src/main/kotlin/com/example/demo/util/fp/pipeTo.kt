package com.example.demo.util.fp

public inline fun <T : Any> T.pipeTo(block: (T) -> T): T {
    return block(this);
}