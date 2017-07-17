package org.codetome.todomvc.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TodomvcKotlinApplication

fun main(args: Array<String>) {
    SpringApplication.run(TodomvcKotlinApplication::class.java, *args)
}
