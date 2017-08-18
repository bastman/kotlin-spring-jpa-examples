package com.example.demo.configuration

import com.example.demo.util.defer.Defer
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.http.HttpHost
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ElasticsearchConfig(
        @Value("\${elasticsearch.host}")
        private val host: String,
        @Value("\${elasticsearch.port}")
        private val port: Int,
        @Value("\${elasticsearch.clustername}")
        private val clusterName: String
) {
    private val defer = Defer()


    @Bean
    fun client(): RestClient {

        val lowLevelRestClient = RestClient.builder(
                HttpHost("localhost", 9200, "http"),
                HttpHost("localhost", 9201, "http")).build()


        defer.addGraceful { lowLevelRestClient.close() }

        return lowLevelRestClient
    }


}

/*
@Configuration
class EsHigh(
        private val lowLevelRestClient:RestClient
) {

    @Bean
    fun highLevel():RestHighLevelClient {
        val highlevelClient = RestHighLevelClient(lowLevelRestClient)

        return highlevelClient
    }
}
*/

