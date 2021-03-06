# Build & Run

## Prerequisites
* A computer with: 
  * internet access
  * some development tools:
    * git
    * a v11 JDK
    * Maven
    * TCP/IP port 8090 is available for use  
    * text editor of your choice
  
### Get the project sources and run the API server:
  * open a terminal on your favoured OS and execute the following to build and run the server:
    ```
        git clone git@github.com:bladewheels/GildedRoseAPI.git
        cd GildedRoseAPI
        mvn spring-boot:run
    ```
  * you should see *something* like this (AFAIK the WARNINGs do not affect the behaviour of the server at this time):  
    ```
        WARNING: An illegal reflective access operation has occurred
        WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
        WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
        WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
        WARNING: All illegal access operations will be denied in a future release
        [INFO] Scanning for projects...
        [INFO]
        [INFO] ---------------< com.miw.homework:sr-java-dev-homework >----------------
        [INFO] Building sr-java-dev-homework 0.0.1-SNAPSHOT
        [INFO] --------------------------------[ jar ]---------------------------------
        [INFO]
        [INFO] >>> spring-boot-maven-plugin:2.4.1:run (default-cli) > test-compile @ sr-java-dev-homework >>>
        [INFO]
        [INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ sr-java-dev-homework ---
        [INFO] Using 'UTF-8' encoding to copy filtered resources.
        [INFO] Using 'UTF-8' encoding to copy filtered properties files.
        [INFO] Copying 1 resource
        [INFO] Copying 0 resource
        [INFO]
        [INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ sr-java-dev-homework ---
        [INFO] Changes detected - recompiling the module!
        [INFO] Compiling 24 source files to /YOUR_PATH/GildedRoseAPI/target/classes
        [INFO] /YOUR_PATH/GildedRoseAPI/src/main/java/com/miw/homework/gildedrose/expanded/security/SecurityConfig.java: /YOUR_PATH/GildedRoseAPI/src/main/java/com/miw/homework/gildedrose/expanded/security/SecurityConfig.java uses unchecked or unsafe operations.
        [INFO] /YOUR_PATH/GildedRoseAPI/src/main/java/com/miw/homework/gildedrose/expanded/security/SecurityConfig.java: Recompile with -Xlint:unchecked for details.
        [INFO]  
        [INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ sr-java-dev-homework ---
        [INFO] Using 'UTF-8' encoding to copy filtered resources.
        [INFO] Using 'UTF-8' encoding to copy filtered properties files.
        [INFO] skip non existing resourceDirectory /YOUR_PATH/GildedRoseAPI/src/test/resources
        [INFO]
        [INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ sr-java-dev-homework ---
        [INFO] Changes detected - recompiling the module!
        [INFO] Compiling 2 source files to /YOUR_PATH/GildedRoseAPI/target/test-classes
        [INFO]
        [INFO] <<< spring-boot-maven-plugin:2.4.1:run (default-cli) < test-compile @ sr-java-dev-homework <<<
        [INFO]
        [INFO]
        [INFO] --- spring-boot-maven-plugin:2.4.1:run (default-cli) @ sr-java-dev-homework ---
        [INFO] Attaching agents: []

        .   ____          _            __ _ _
        /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
        ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
        \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
        '  |____| .__|_| |_|_| |_\__, | / / / /
        =========|_|==============|___/=/_/_/_/
        :: Spring Boot ::                (v2.4.2)

        2021-01-22 18:58:27.372  INFO 231213 --- [           main] c.m.h.g.expanded.TheGildedRoseExpanded   : Starting TheGildedRoseExpanded using Java 11.0.9.1 on localhost with PID 231213 (/YOUR_PATH/GildedRoseAPI/target/classes started by dev in /YOUR_PATH/GildedRoseAPI)
        2021-01-22 18:58:27.374  INFO 231213 --- [           main] c.m.h.g.expanded.TheGildedRoseExpanded   : No active profile set, falling back to default profiles: default
        2021-01-22 18:58:28.177  INFO 231213 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8090 (http)
        2021-01-22 18:58:28.188  INFO 231213 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
        2021-01-22 18:58:28.189  INFO 231213 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.41]
        2021-01-22 18:58:28.237  INFO 231213 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
        2021-01-22 18:58:28.238  INFO 231213 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 816 ms
        2021-01-22 18:58:28.343  INFO 231213 --- [           main] o.s.boot.web.servlet.RegistrationBean    : Filter tokenAuthenticationFilter was not registered (disabled)
        2021-01-22 18:58:28.424  INFO 231213 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure Or [Ant [pattern='/public/**'], Ant [pattern='/error/**']] with []
        2021-01-22 18:58:28.452  INFO 231213 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@3f1ef9d6, org.springframework.security.web.context.SecurityContextPersistenceFilter@44065156, org.springframework.security.web.header.HeaderWriterFilter@9b9a327, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@574b7f4a, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@57cb70be, com.miw.homework.gildedrose.expanded.security.TokenAuthenticationFilter@767191b1, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@17461db, org.springframework.security.web.session.SessionManagementFilter@358ab600, org.springframework.security.web.access.ExceptionTranslationFilter@1f03fba0, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@514de325]
        2021-01-22 18:58:28.577  INFO 231213 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
        2021-01-22 18:58:28.730  INFO 231213 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8090 (http) with context path ''
        2021-01-22 18:58:28.739  INFO 231213 --- [           main] c.m.h.g.expanded.TheGildedRoseExpanded   : Started TheGildedRoseExpanded in 1.727 seconds (JVM running for 2.131)
        2021-01-22 18:59:11.701  INFO 231213 --- [nio-8090-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
        2021-01-22 18:59:11.701  INFO 231213 --- [nio-8090-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
        2021-01-22 18:59:11.702  INFO 231213 --- [nio-8090-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
    ```
  * If SpringBoot fails to start due to *port already in use* you can override the default *8090* in **src/main/resources/application.properties**


  * Press Ctrl-C or close the terminal to stop the server, you may see:
    ```
        2021-01-22 19:16:54.253  INFO 231213 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time:  18:32 min
        [INFO] Finished at: 2021-01-22T19:16:54-07:00
        [INFO] ------------------------------------------------------------------------
    ```
  * Tests can also be run from the commandline e.g. **mvn install**
    ```
        WARNING: An illegal reflective access operation has occurred
        WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
        WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
        WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
        WARNING: All illegal access operations will be denied in a future release
        [INFO] Scanning for projects...
        [INFO]
        [INFO] ---------------< com.miw.homework:sr-java-dev-homework >----------------
        [INFO] Building sr-java-dev-homework 0.0.1-SNAPSHOT
        [INFO] --------------------------------[ jar ]---------------------------------
        [INFO]
        [INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ sr-java-dev-homework ---
        [INFO] Using 'UTF-8' encoding to copy filtered resources.
        [INFO] Using 'UTF-8' encoding to copy filtered properties files.
        [INFO] Copying 1 resource
        [INFO] Copying 0 resource
        [INFO]
        [INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ sr-java-dev-homework ---
        [INFO] Nothing to compile - all classes are up to date
        [INFO]
        [INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ sr-java-dev-homework ---
        [INFO] Using 'UTF-8' encoding to copy filtered resources.
        [INFO] Using 'UTF-8' encoding to copy filtered properties files.
        [INFO] skip non existing resourceDirectory /YOUR_PATH/GildedRoseAPI/src/test/resources
        [INFO]
        [INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ sr-java-dev-homework ---
        [INFO] Changes detected - recompiling the module!
        [INFO] Compiling 2 source files to /YOUR_PATH/GildedRoseAPI/target/test-classes
        [INFO]
        [INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ sr-java-dev-homework ---
        [INFO]
        [INFO] -------------------------------------------------------
        [INFO]  T E S T S
        [INFO] -------------------------------------------------------
        [INFO] Running com.miw.homework.gildedrose.expanded.services.InMemoryInventoryServiceTest
        [INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.723 s - in com.miw.homework.gildedrose.expanded.services.InMemoryInventoryServiceTest
        [INFO]
        [INFO] Results:
        [INFO]
        [INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
        [INFO]
        [INFO]
        [INFO] --- maven-jar-plugin:3.2.0:jar (default-jar) @ sr-java-dev-homework ---
        [INFO] Building jar: /YOUR_PATH/GildedRoseAPI/target/sr-java-dev-homework-0.0.1-SNAPSHOT.jar
        [INFO]
        [INFO] --- spring-boot-maven-plugin:2.4.1:repackage (repackage) @ sr-java-dev-homework ---
        [INFO] Replacing main artifact with repackaged archive
        [INFO]
        [INFO] --- maven-install-plugin:2.5.2:install (default-install) @ sr-java-dev-homework ---
        [INFO] Installing /YOUR_PATH/GildedRoseAPI/target/sr-java-dev-homework-0.0.1-SNAPSHOT.jar to /YOUR_PATH/.m2/repository/com/miw/homework/sr-java-dev-homework/0.0.1-SNAPSHOT/sr-java-dev-homework-0.0.1-SNAPSHOT.jar
        [INFO] Installing /YOUR_PATH/GildedRoseAPI/pom.xml to /YOUR_PATH/.m2/repository/com/miw/homework/sr-java-dev-homework/0.0.1-SNAPSHOT/sr-java-dev-homework-0.0.1-SNAPSHOT.pom
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time:  7.738 s
        [INFO] Finished at: 2021-01-22T19:23:42-07:00
        [INFO] ------------------------------------------------------------------------
    ```
# Prerequisites & caveats for API usage:
It is *required* that the user of this API has **already** signed up with a valid email address *(for the purposes of Billing/Shipping)* with our corporate office at https://gildedrose.com/accounts .

### For demo purposes the following email addresses can be used:
* customer_x@go.to
* customer_x@the.wang 
* customer_x@us.online
* customer_x@cheese.pizza

### Pricing:
* All prices are represented by integer units e.g. pennies in US/Canadian terms
* **Surge pricing** is a feature we implement to offset our costs when under high load. It is in effect when there have been more than 10 API calls to list our inventory over the course of the previous hour. *Surge pricing* is 110% of our normal list prices.

### Trying out the API

#### The following endpoints are available:
| Use Case | HTTP Verb | Endpoint | Sequence Diagram(s) |
| :------- | :-------- | :------: | :------: |
| **Register** with a valid email address | POST | /public/users/register | ![Registration success](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Sqx3i8m40RXdbF01RmzLQWG7U86ZlqhjhHUXtqermT4DTDzYtbMZy6dIvV2KH7Av-Q-svL8yv6sxYtMC6YPuX1FPGvQELSS3M2ZU-UA-fg6s_D18tSXq_8VXStG0YF3EocsRWvD-qzS8FNR3m00) ![Registration with invalid email](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Oqx3i8m40LxJW4NyFPKeK9qY1iuzbEoqjeE-o7N9uXgHffY9lEi7arE5-v5eY9KpglxPbSYpNjQsDUOmw1bT4VtL4QZMNB5lW9YT6UfgAzXk3qVr39tCYr_KTW8BJ0ol9DYONDemX4bSVzauPgsttz2hmU_) |
| **Retrieve** a complete list of Items | GET | /private/inventory/list | ![Inventory list](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Sqx3i8m40RXdbF01RmzLQWG7U86ZlqBMrglmpwKwmDADTDzYtbMZy6dIvV2KH7Av-Q-sPL8yvxUtLkiOT0onI6UoXoqSgxO6y16zyuLzJKDj-U3Hkv2fkKOXStG0YF39t4tJzjVMo3rroy0)|
| **Buy** a quantity of an Item -- i.e. by Inventory (Item) ID; *these are the **IDs** exposed in the Inventory list* | POST | /private/inventory/buy/#/ofItem/# | ![Buy InventoryItem](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Ssx3G8n40RXd2gW0MzET4I83D61p_w5BQtNnpvqedj0H3FPjvXdzHXyE7Kf71MXV6pkcvs9DE_fsRt56WOjKnpYgSn1A-UAlG5iTErSKH_JSBdVQEGkP5h-ex0PMc1aUAKrtcdxOGkaVli1) |

[//]: # (The above UML image was created using direction from: https://stackoverflow.com/a/32771815; the use of cache=no means that updates to the raw *.puml files will not be cached in readers' browsers)
[//]: # (i.e. go to plantUML website and put this into the form to generate the plantUML URL used above..)
[//]: # (@startuml
!includeurl https://raw.githubusercontent.com/bladewheels/GildedRoseAPI/main/src/main/resources/arch.protected.puml
@enduml)
[//]: # (DO NOT forget to change 'png' to 'svg' in the URL copy/pasted from the PlantUML form generator - or the URL will not work)

#### Example Requests/Responses:
* Try the **secured** Inventory API **w/o authentication**:
    * ```curl http://localhost:8090/private/inventory/list```
      * returns the JSON: 
          ``` JSON
            {
                "timestamp":"2021-01-18T21:27:38.440+00:00",
                "status":401,
                "error":"Unauthorized",
                "message":"",
                "path":"/private/inventory/list"
            }
          ```

* Try *again* with an **invalid authentication token**:
    * ```curl -H 'Authorization: Bearer asdf-qwerty-12345' http://localhost:8090/private/inventory/list```
        * returns the JSON:
            ``` JSON
              {
                  "timestamp":"2021-01-18T21:27:38.440+00:00",
                  "status":401,
                  "error":"Unauthorized",
                  "message":"",
                  "path":"/private/inventory/list"
              }
            ```
          
* Try to *register* for API use with an **unrecognized** (see Prerequisites & caveats, above) **email**:
    * ```curl -X POST -d 'email=a.b@c.de' http://localhost:8090/public/users/register```
        * returns the message:
          ```
          Sorry, that email address is not setup for shipping/billing - please visit https://gildedrose.com/accounts to set that up first.
          ```
                  
* Register for API use with a **recognized email**:
    * ```curl -X POST -d 'email=customer_x@go.to' http://localhost:8090/public/users/register```
      * returns the message containing an *authentication token*: 
        ```
        Welcome customer_x@go.to, please use the following API token to shop at Gilded Rose ===>eaa12ff9-db63-405a-bbe9-4edec303c98b<===.
        ```
        
* GET the current Inventory w/valid authentication token:
    * ```curl -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/list```
        * returns the JSON:
            ``` JSON
              [
                {
                    "id": 5,
                    "item": {
                        "name": "A Shiny thing",
                        "description": "A really quite shiny thing!",
                        "price": 75612
                    },
                    "stockLevel": {
                        "current": 185
                    }
                }, 
                {
                    "id": 6,
                    "item": {
                        "name": "Something else ENTIRELY",
                        "description": "WOW, what a thing to sell!",
                        "price": 45612
                    },
                    "stockLevel": {
                        "current": 14
                    }
                }, 
                {
                    "id": 2,
                    "item": {
                        "name": "Such a thing",
                        "description": "Sheesh, what is this thing?",
                        "price": 15309
                    },
                    "stockLevel": {
                        "current": 13
                    }
                }, 
                {
                    "id": 4,
                    "item": {
                        "name": "Thing 0x001",
                        "description": "A reeallly nice thing!",
                        "price": 35612
                    },
                    "stockLevel": {
                        "current": 66
                    }
                }, 
                {
                    "id": 3,
                    "item": {
                        "name": "WTF",
                        "description": "Huh?",
                        "price": 5612
                    },
                    "stockLevel": {
                        "current": 218
                    }
                }, 
                {
                    "id": 1,
                    "item": {
                        "name": "Yet Another Thing",
                        "description": "So plain, SO non-descriptive...",
                        "price": 55612
                    },
                    "stockLevel": {
                        "current": 34
                    }
                }
              ]
            ```

* Try to buy an Item, but using an **invalid** HTTP method (e.g. GET):
    * ```curl -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/1/ofItem/5```
        * returns the JSON:
            ``` JSON
              {
                "timestamp":"2021-01-21T16:29:10.557+00:00",
                "status":405,
                "error":"Method Not Allowed",
                "message":"",
                "path":"/private/inventory/buy/1/ofItem/5"
              }
            ```         
          
* Try to buy an Item, but using an **invalid** (i.e. non-numeric) **Item identifier**:
    * ```curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/321/ofItem/xxx```
        * returns the JSON:
            ``` JSON
              {
                "orderId":"n/a",
                "quantity":0,
                "priceEach":0,
                "totalCharged":0,
                "notice":"Sorry, this Item is no longer available for purchase."
              }
            ```          
The messaging here is a little opaque here due to the fact that a *DiscontinuedItem* model is being re-used here 
(I'm too lazy/busy to improve it for now, there's a TODO in the source code). 

Hopefully its enough to get a caller looking in the right direction...
until I fix it (I'll probably throw a custom Exception and figure out a standard messaging format to serialize back to callers).
      
* Try to buy an Item, but using an **invalid** (i.e. non-numeric) quantity:
    * ```curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/asdf/ofItem/1```
        * returns the JSON:
            ``` JSON
                {
                    "orderId":"n/a",
                    "itemFromInventory":{
                        "id":5,
                        "item":{
                            "name":"Malformed quantity provided",
                            "description":"i.e. quantity = 454asdf",
                            "price":0
                        },
                        "stockLevel":{
                            "current":0
                        }
                    },
                    "quantity":0,
                    "inStock":0,
                    "notice":"Sorry, we don't have enough of this Item in stock at the moment - please try again later or purchase less Items."
                }
            ```      
Similar to the last case (above), the messaging here is a little opaque here due to the fact that a *UnderStockedItem* model is being re-used here 
(I'm too lazy/busy to improve it for now, there's a TODO in the source code).

Hopefully its enough to get a caller looking in the right direction...
until I fix it (I'll probably throw a custom Exception and figure out a standard messaging format to serialize back to callers).

* Try to buy an Item, but using an **invalid** Item identifier (i.e. numeric, but not available from inventory):
    * ```curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/321/ofItem/666```
        * returns the JSON:
            ``` JSON
                {
                    "orderId":"n/a",
                    "quantity":0,
                    "priceEach":0,
                    "totalCharged":0,
                    "notice":"Sorry, this Item is no longer available for purchase."
                }
            ```

* Try to buy an Item, but asking for **more than we have in stock**:
    * ```curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/4096/ofItem/5```
        * returns the JSON:
            ``` JSON
                {
                    "orderId":"n/a",
                    "itemFromInventory":{
                        "id":1,
                        "item":{
                            "name":"Yet Another Thing",
                            "description":"So plain, SO non-descriptive...",
                            "price":55612
                        },
                        "stockLevel":{
                            "current":16
                        }
                    },
                    "quantity":67,
                    "inStock":16,
                    "notice":"Sorry, we don't have enough of this Item in stock at the moment - please try again later or purchase less Items."
                }
            ```      
    
* Buy 1 of an Item, at its *list* price:
    * ```curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/1/ofItem/5```
        * returns the JSON:
            ``` JSON
              {
                "orderId":"8f6879d1-4689-4ec8-b6d2-152b229b2fc4",
                "itemFromInventory": {
                    "id":5,
                    "item": {
                        "name":"A Shiny thing",
                        "description":"A really quite shiny thing!",
                        "price":75612
                    },
                    "stockLevel": {
                        "current":184
                    }
                },
                "quantity":1,
                "priceEach":75612.0,
                "totalCharged":75612.0,
                "notice":"Surge pricing may have inflated the list price of this Item."
              }
            ```      

* Buy an Item, at its *surge* price (see Prerequisites & caveats, above):
    * ```curl -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8090/private/inventory/buy/2/ofItem/1```
        * returns the JSON:
            ``` JSON
              {
                "orderId":"359f8e1b-625d-48a7-b978-17e4ee52aa84",
                "itemFromInventory":{
                    "id":1,
                    "item":{
                        "name":"Yet Another Thing",
                        "description":"So plain, SO non-descriptive...",
                        "price":55612
                    },
                    "stockLevel":{
                        "current":69
                    }
                },
                "quantity":2,
                "priceEach":61173,
                "totalCharged":122346,
                "notice":"Surge pricing may have inflated the list price of this Item."
              }
            ```     
                      


###### Terms & Conditions of Use:

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum commodo ex in lacus vestibulum iaculis. Vestibulum non scelerisque quam. Integer faucibus purus ipsum, sed aliquet velit posuere sed. Nulla ut dui suscipit, hendrerit nisi sit amet, aliquet tortor. Suspendisse potenti. Fusce lorem metus, viverra sit amet ullamcorper sed, tempus id augue. Vestibulum vitae libero aliquam, hendrerit lectus vel, maximus urna. Donec dapibus ex sit amet mi rutrum pulvinar.

Suspendisse quis sem tortor. Sed eget lacus in nibh auctor suscipit. Morbi vel tincidunt velit. Vestibulum egestas ultricies mauris ut egestas. Mauris et metus eget turpis consectetur ullamcorper quis non metus. Etiam accumsan nisi quis nisl dictum commodo. Integer magna elit, vestibulum at augue at, congue suscipit tellus. Maecenas lorem ex, blandit egestas pellentesque ut, sollicitudin id lacus. In blandit ipsum quis nibh dictum convallis. Proin porttitor, odio nec porttitor scelerisque, est velit rutrum mi, non egestas tortor metus sit amet neque. Cras nec diam id nibh efficitur feugiat. Etiam id feugiat velit.

Nam semper eros non imperdiet efficitur. Cras dictum, tortor nec tempor laoreet, diam felis auctor est, quis auctor ante neque nec nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut mauris mi, rhoncus sit amet lacus in, molestie sagittis mi. Praesent ligula nibh, euismod eleifend orci sit amet, tempus luctus nisi. Integer non nisi sodales, accumsan lectus ut, finibus quam. Aenean non pretium eros. Cras quis turpis quis libero euismod commodo interdum id neque. Proin hendrerit dui eu arcu congue convallis. Proin ut sodales odio, at placerat quam. Proin placerat in urna ut mollis. Etiam luctus ante quam, eget faucibus dolor placerat mollis.
