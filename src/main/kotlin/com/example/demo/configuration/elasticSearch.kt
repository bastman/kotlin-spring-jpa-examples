package com.example.demo.configuration

import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.node.NodeBuilder.nodeBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import java.net.InetAddress

@Configuration
@EnableElasticsearchRepositories(
        basePackages = arrayOf(
                "com.example.demo.api.bookstore.domain.entities"
        )
)
class EsConfig(
        @Value("\${elasticsearch.useEmbedded}")
        private val embedded: Boolean,
        @Value("\${elasticsearch.host}")
        private val host: String,
        @Value("\${elasticsearch.port}")
        private val port: Int,
        @Value("\${elasticsearch.clustername}")
        private val clusterName: String
) {

    @Bean
    fun createClient(): Client {
        if (embedded) {
            val currentWorkingDir = System.getProperty("user.dir");
            val settingsBuilder = nodeBuilder().local(true).settings
            settingsBuilder.put("path.home", currentWorkingDir)
            val builder = nodeBuilder().local(true).settings(settingsBuilder.build())
            val node = builder.node()
            return node.client()
        } else {
            val esSettings = Settings.settingsBuilder()
                    .put("cluster.name", clusterName)
                    .build();

            //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
            return TransportClient.builder()
                    .settings(esSettings)
                    .build()
                    .addTransportAddress(
                            InetSocketTransportAddress(InetAddress.getByName(host), port)
                    );
        }
    }
}
