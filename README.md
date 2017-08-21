# kotlin-spring-jpa-examples
- playground for kotlin + spring-boot + bean-validation + jpa + hibernate + querydsl
- realworld examples (just a little simplified): check api.realestate

# status: 
- its an active playground ,therefore in progress ...

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

### hibernate: 
- if you have to use if for whatever reason, check the logs ;) ...
### hibernate: immutable data class as @Entity
- in jpa/hibernate context it gets proxied by cglib & bean validation works
- entity.copy() -> triggers init() method, if there is one
### hibernate: auditing
- Auditing classes (Listeners) are no spring beans
- @inject will not work out-of-the-box
- thefore its pretty useless
### jetbrains gradle plugin:

- https://github.com/JetBrains/kotlin/blob/master/libraries/tools/kotlin-gradle-plugin/Module.md

### querydsl: is awesome :)

- https://github.com/JetBrains/kotlin-examples/blob/master/gradle/kotlin-querydsl/build.gradle
- https://github.com/eugenp/tutorials/tree/master/querydsl
- example implementation: 

        com.example.demo.api.realestate.PropertiesCrudController.search()
    
### elasticsearch
- https://www.mkyong.com/spring-boot/spring-boot-spring-data-elasticsearch-example/
- spring-data-elasticsearch: dependency hell: 
    - boot 1.5.* with spring-data-elasticsearch does not work with es 5.5
    - es 5.5 requires spring-data-elasticsearch 2.* (boot 2.*)
    - using es transportclient (native protocol) is not recommended by es
    - es recommends using rest-api - which is aware of future versions of es
    - es is currently working on a high level restapi client that should make things easy
    - most cloud providers (hosted-es-as-a-service) just allow using rest api   
    - several issues related to jackson-json serialization (e.g.: supports JodaTime but not Java 8 Time Api out-of-the-box) 