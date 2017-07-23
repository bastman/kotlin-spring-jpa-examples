package com.example.demo

import org.springframework.boot.SpringApplication

fun main(args: Array<String>) {
    SpringApplication(DemoApplication::class.java)
            .run(*args)
}