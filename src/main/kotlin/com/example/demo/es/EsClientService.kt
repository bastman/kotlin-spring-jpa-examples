package com.example.demo.es

import com.example.demo.configuration.JacksonConfig
import org.elasticsearch.client.Response
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.http.entity.ContentType
import org.elasticsearch.client.http.nio.entity.NStringEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class EsClientService(
        private val restClient: RestClient
) {

    private val objectMapper = JacksonConfig.defaultMapper()

    fun put(endpoint: String, payload: Any): Response {
        return restClient.performRequest(
                "PUT",
                //"/twitter/foo/1",
                endpoint,
                Collections.emptyMap<String, String>(),
                NStringEntity(
                        objectMapper.writeValueAsString(payload),
                        ContentType.APPLICATION_JSON
                )
        )
    }

}