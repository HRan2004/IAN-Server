package com.hraps.ianserver

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.hraps.ianserver.mapper.**")
class IanServerApplication

fun main(args: Array<String>) {
    runApplication<IanServerApplication>(*args)
}
