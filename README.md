# kotlin-spring-jpa-examples
playground for kotlin, spring boot and jpa

# status: in progress ...


# requirements

- postgres

## create an example db
    $ make db.local.create 
    
    
## additional resources

### bean validation

- https://stackoverflow.com/questions/44320678/jsr-349-bean-validation-for-spring-restcontroller-with-spring-boot
- http://apprize.info/javascript/wrox/16.html
- https://github.com/gothinkster/kotlin-spring-realworld-example-app/blob/master/src/main/kotlin/io/realworld/ApiApplication.kt
- https://github.com/arawn/kotlin-spring-example/blob/master/src/main/kotlin/org/ksug/forum/web/ForumRestController.kt


## findings

### immutable data class as @Entity
- in jpa/hibernate context it gets proxied by cglib & bean validation works
- entity.copy() -> triggers init() method, if there is one

## querydsl
-https://github.com/spring-projects/spring-data-examples/tree/master/web/querydsl

