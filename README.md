# GildedRoseAPI

## This is a HTTP/REST API server for a fictitious entity, 'The Gilded Rose'.

### The requirements are: 
#### "...to produce a public HTTP-accessible API. The Gilded Rose would like to utilize a **surge pricing** model like Uber to increase the price of *items* by 10% if they have been **viewed** *more than 10 times in an hour*."
   * i.e.:
     * **Retrieve** the current inventory (i.e. list of items)
     * **Buy** an item (user must be authenticated)
     * Here is the definition for an item:
         ```C#
         class Item 
         { 
            public    String    Name {get; set;} 
            public    String    Description {get; set;} 
            public    int       Price {get; set;} 
         }
         ```

### A couple of **questions** to consider:

#### How do we know if a user is *authenticated*?

* **MY ANSWERs**: 
  * API users provide something that only the server knows i.e. a **token** provided to them during the **registration** process
  * API users to the API provide an *Authorization* HTTP Header of the form:
    * *Bearer **token***


#### Is it *always possible* to buy an item?
* **MY ANSWERs**:
  * API users can **buy** Items if:
    * the Item is in the inventory
    * the quantity in stock is greater than zero
    * if the requested quantity is less than or equal to the quantity in stock
      * otherwise, they can try again, requesting a lesser quantity

#### Please model (and test!) accordingly. Using **a real database is not required**.
* Service-layer unit tests are [**complete**](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/test/java/com/miw/homework/gildedrose/expanded/services/InMemoryInventoryServiceTest.java).
  * I used constructor-based dependency injection in the system under test to avoid spinning up the Spring container for these tests.
* Controller-layer unit tests are [**complete**](https://github.com/bladewheels/GildedRoseAPI/blob/main/README.md#my-backlog-is-software-ever-done).
  * I found figuring out how to run these against the secured endpoints, without spinning up the Spring container took much longer than I expected.
* Integration tests are currently [**manually done w/curl**](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#example-requestsresponses)


#### Deliverables:
* A runnable system with [instructions on how to build/run your application](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#build--run)
* The application should be built using Java and the Spring framework
* The application should be able to be [run from the command line](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#get-the-project-sources-and-run-the-api-server) without any dependencies or database’s required to run your application.  (Other than maven, gradle and Java)
* A system that can process the [two API requests](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#the-following-endpoints-are-available) via HTTP
* Appropriate [tests](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#get-the-project-sources-and-run-the-api-server) (unit, integration etc)
  

##### A quick explanation of:
###### Your application, how you set it up, how it was built, how you designed the surge pricing and the type of architecture chosen.
* The tech stack is: Java (11), SpringBoot (2.x), HTTP, JSON
  * I felt these technologies would be the best options for a team already using SpringBoot.
  
[//]: # (The CRLF above is needed, lest this comment become visible to readers; TODO: Add UML that clarifies the relationship between Item and InventoryItem)
* I chose to implement a layered architecture with features of MVC and REST as I am very familiar with this style and it seemed applicable to the requirements. It is a widely used pattern that many developers will easily recognize and be able to support, modify and extend.
  * The application broadly comprises 3 layers i.e. web wrangling, business logic and data storage:

    * ![Registration](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Sqz3i8m34VndLF01UgTgKo83N63rFcBH7ASoX_gzG4nlRVzLezYaKDEbwuiMP4cvnQn-vN8oh6yUxJSqc4yDQ2ny1oqIQau6Y1EzouLzJKTj-U3HkbARlmVWyyqXbCnRil-arPe_VO3)
    * ![Shopping](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Sqz3i8m34VndLF01UATgKo83N63rFwBH7AJoX_gzG4nlRVz5ezgaPkUBdsmfY1DporYzokHrQEyUxHSqs4yHs14uRdHgbLmD42Txbq5yfgEs_D1M-s3tFW_1fzf3ATgtCfkCy01AVlFN33asmy0)

      * A [public HTTP **Controller**](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/main/java/com/miw/homework/gildedrose/expanded/controllers/PublicUsersController.java) offering an [endpoint](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#the-following-endpoints-are-available) for registering new Users.
      * A [private HTTP **Controller**](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/main/java/com/miw/homework/gildedrose/expanded/controllers/PrivateInventoryController.java) offering [2 endpoints](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#the-following-endpoints-are-available) for listing or buying InventoryItems.
      * A [business logic **Service** that manages the registration and authentication of Users](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/main/java/com/miw/homework/gildedrose/expanded/security/user/services/UserService.java).
      * A [business logic **Service** that manages InventoryItems' data marshaling , surge pricing and views](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/main/java/com/miw/homework/gildedrose/expanded/services/InventoryService.java).
      * A [data persistence **Service** that handles InventoryItems' data and surge pricing metadata, in-memory](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/main/java/com/miw/homework/gildedrose/expanded/services/InventoryStorage.java).
  
[//]: # (The above UML images were created using direction from: https://stackoverflow.com/a/32771815; the use of cache=no means that updates to the raw *.puml files will not be cached in readers' browsers)
[//]: # (i.e. go to PlantUML website 
e.g. http://www.plantuml.com/plantuml/uml/9Sqz3i8m34VndLF01UATgKo83N63rFwBH7AJoX_gzG4nlRVz5ezgaPkUBdsmfY1DporYzokHrQEyUxHSqs4yHs14uRdHgbLmD42Txbq5yfgEs_D1M-s3tFW_1fzf3ATgtCfkCy01AVlFN33asmy0 
and put this e.g. :=>)
[//]: # (
@startuml
!includeurl https://raw.githubusercontent.com/bladewheels/GildedRoseAPI/main/src/main/resources/arch.protected.puml
@enduml)
[//]: # (<= into the web form to generate the plantUML URL used above..)
[//]: # (DO NOT forget to change 'png' to 'svg' in the URL copy/pasted from the PlantUML form generator - or the URL will not work)
[//]: # (Or, you can click on the Open SVG link and copy the URL from there)

* Use cases:
  * ![API User interaction diagram](http://www.plantuml.com/plantuml/proxy?cache=no&src=http://www.plantuml.com/plantuml/svg/9Oqx3i8m40LxJW4NyFPKeK9qY1iuzXEsj5v7-r6kJr2gcMfcCS_gCVXowr8uAaBvsjmtknDftjEtUuir35gcECHJcODMpXLx0zZesRcYVgRXyNxHo5t8j9oYi1bQO7GKqlOVBP1wy0S0)


* **Surge pricing** design:
  * I needed to persist the timestamps of recent *views* of inventory
    * I was sorely tempted, at first, to use an in-memory DB like H2 to store them but it seemed a bit overkill for the requirements. 
      * And, I wasn't entirely sure you wouldn't spank me for bending the guidelines a bit 8^)
    * ultimately I chose a thread-safe JDK Collection type as in-memory persistence
    * since the data access was so fast I simply *created/stored* the timestamp whenever the inventory was queried
  * I simply stream/filtered the *view timestamps* and determined the need for **Surge pricing** based upon the *size* of the result
    * see the following methods at the bottom of [the Inventory Service implementation](https://github.com/bladewheels/GildedRoseAPI/blob/main/src/main/java/com/miw/homework/gildedrose/expanded/services/InMemoryInventoryService.java) for details:
      * getSurgeOrListPrice()
      * findNumberOfViews()
  

* How I built it:
  * See the **authentication mechanism** section below for more details on how I got started
    * I felt the *authentication mechanism* was my greatest unknown so I started my research on how-to there...
###### Choice of *data format*. Include one example of a request and response.
* JSON serialization (w/Jackson)
  * See [USAGE.md](https://github.com/bladewheels/GildedRoseAPI/blob/main/USAGE.md#example-requestsresponses) for example Request/Responses.
  
###### What *authentication mechanism* was chosen, and why?
* I had never used Spring Security before but:
  * I felt it would be the most acceptable option to a team already using Spring.
  * I briefly searched for the simplest possible mechanism to start with, recognising that it could probably be further hardened later.
    * props to this blog post/author:
      * https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/
      * https://github.com/jloisel/securing-rest-api-spring-security
  * I landed upon this approach (as the simplest thing that could possibly work/actually get done on time):
    * returning a UUID token in response to a GET to a public (i.e. unauthenticated) URL
    * requiring a (UUID) token in the Authorization HTTP Header of any private URLs.

###### My backlog (is software ever 'done'?)
* Service layer:
  * TODO: throw Exceptions instead of returning Model(s) to Controller
  * DONE: ~~migrate Model creation on startup to Data layer~~
  * DONE: ~~update unit tests accordingly~~
* Controller layer:
  * TODO: Explicitly(?) Accept/return JSON
  * TODO: throw Exceptions on bad inputs
  * TODO: introduce Exception handlers that:
    * TODO: map to status codes
    * TODO: return JSON
  * DONE: implement unit tests
    * DONE: ~~Unsecured User Controller~~
    * DONE: ~~Secured User Controller~~
    * DONE: ~~Secured Inventory Controller~~
* Documentation:
  * TODO: update text & UML diagrams wrt/above
  * TODO: *nice to have*: Swagger or similar
* Architecture:
  * TODO: consider [feature-focused code organization](https://builtwithdot.net/blog/changing-how-your-code-is-organized-could-speed-development-from-weeks-to-days)
  * TODO: consider [hexagonal architecture](https://medium.com/swlh/a-practical-example-of-applying-java-modules-in-a-hexagonal-architecture-d345deec654b)


###### Here's where I over-communicate some things I wondered about/discarded when I was getting started:
*  ~~use HATEOS?~~ [Not enough time]
* ~~use JSON schemas?~~ [Not enough time]
* introduce & expose Item IDs? [X]
* /list endpoint:
  * ~~filter w/query params?~~ [Nope]
  * ~~ranges e.g. names starting with? IDs?~~ [Nope]
  * ~~pagination?~~ [Nope]
    * ~~(variable?) page size?~~ [Nope]
    * how to determine serverside *viewed* status, for each item, for the purposes of determing *surge pricing*?
      * use thread-safe LongAdder, incrementing when /inventory called? [Nope, used CopyOnWriteArrayList<LocalDateTime instead]]
  * ~~consciously choose to return *in-stock* yes/no attribute with each item~~ [Nope]
    * ~~i.e. rather than *not* returning items that *are* out-of-stock~~
    * ~~this allows callers to parallelize their *get-all, by-page* calls *without* inadvertently missing/duplicating items that go in or out of stock during the query time~~
* /buy endpoint:
  * ~~quantity defaults to: 1~~
  * ~~return 400 if quantity > in-stock count?~~
  * how to safely (i.e. ACID) handle concurrent decrements to stock level?
    * use thread-safe LongAdder, incrementing when /inventory called
  * where (if at all) to persist buys? e.g. item, quantity, price, buyer?
    * returned Model objects (no persistence, but easily added) serialized as JSON
  * does surge pricing apply if fulfilling *part* of executing a buy request tips the items/hour *over* the threshold?
    * Nope
  * **should** items have a pre-calculated *surge price* so as to avoid onerous calculating when calling /list?
    * Nope
    * or, would /list calls **just** advertise the *normal* price?
* purchases should result in a one-line description e.g.
  * itemID totalQuantity surgeQuantity price surgePrice totalPrice
   * returned Model objects, serialized as JSON
* *surge pricing*:
  * applies to 10 times in current ~~*wallclock* hour or~~ *duration* hour?
    * likely that *last 60 minutes* is implied in **surge** terminology
  * concurrent listings of items need to be aggregated & processed by surge pricer
    * data structure for surge pricer process?
      * ~~ring buffer or dequeue or ...?~~
      * simple (unbounded) List, needs pruning...
      * needs rolling time window to determine surge
      * simplest: DB View like: COUNT(views) WHERE viewedAt >= (NOW() - 1 hour) GROUP BY ItemID
        * needs cleanup script to cull old views, @Scheduled?
   * ~~round *up* or *down* when calculating? e.g. 1.1 x 1225 = 1238 or 1237?~~
     * Math.floor()
* generate API docs from test w/Spring Docs? [Maybe...]
* ~use H2 DB and persist to local file? [Neither]~
  * Or, just use HashMap as DB?
    * key on Item ID
  * *bootstrap* process should use *deterministic* **ItemIDs and prices** to ease integration/unit  testing
    * **names & descriptions** too?
* authorization:
  * ~~differentiate between SHOPPER and ADMINISTRATOR roles:~~
    * SHOPPERs can call /list and /buy, as above
    * ~~ADMINISTRATORs can, in addition call:~~
      * ~~/add, for adding new Items~~
      * ~~/remove, for removing Items~~
      * ~~/stock, for changing stock levels of Items in the inventory~~

