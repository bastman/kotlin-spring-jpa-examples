package com.example.demo

import com.example.demo.api.bookstore.domain.entities.Book
import com.example.demo.api.bookstore.domain.services.BookService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations





@SpringBootApplication
@EnableSwagger2

@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
class DemoApplication(
        private val es: ElasticsearchOperations,
        private val bookService: BookService
) :CommandLineRunner{



    //@Autowired
    //private val bookService: BookService? = null
    override fun run(vararg args: String?) {
        printElasticSearchInfo()

        bookService.save(Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"))
        bookService.save(Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"))
        bookService.save(Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"))

        //fuzzey search
        val books = bookService.findByAuthor("Rambabu", PageRequest(0, 10))


        println("==== ALL BOOKS ====")
        books.forEach { println(it) }
        println("==== BY TITLE ====")
        val foundBooks = bookService.findByTitle("Elasticsearch Basics");
        println(foundBooks)
    }

    //useful for debug, print elastic search details
    private fun printElasticSearchInfo() {

        println("--ElasticSearch--")
        val client = es.getClient()
        val asMap = client.settings().getAsMap()

        asMap.forEach({ k, v -> println(k + " = " + v) })
        println("--ElasticSearch--")
    }
}



