package com.example.demo.configuration

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.inject.Provider
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Configuration
class QuerydslConfig(
        @PersistenceContext
        private val em: EntityManager
) {

    /*
    @Bean
    fun getJPAQueryFactory2():JPAQueryFactory {
        val provider=object : Provider<EntityManager> {
            override fun get(): EntityManager {
                return em
            }
        }
        return JPAQueryFactory(provider)
    }



    val jpaQueryFactory: JPAQueryFactory
        @Bean
        get() {
            val provider = Provider<EntityManager> { em }
            return JPAQueryFactory(provider)
        }
*/
}