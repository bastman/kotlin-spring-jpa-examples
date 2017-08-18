package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication
@EnableSwagger2

@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
class DemoApplication(

) : CommandLineRunner {

    override fun run(vararg args: String?) {
        /*
        val r=restClient.performRequest("GET", "/",
                Collections.singletonMap("pretty", "true"))
                */
    }


}
