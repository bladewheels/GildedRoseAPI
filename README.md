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
* Service-layer unit tests are **complete**
* Controller-layer unit tests are currently **non-existent**
* Integration tests are currently **non-existent**


#### Deliverables:
###### **The following are documented in USAGE.md**:
* A runnable system with instructions on how to build/run your application
* The application should be built using Java and the Spring framework
* The application should be able to be run from the command line without any dependencies or database’s required to run your application.  (Other than maven, gradle and Java)
* A system that can process the two API requests via HTTP
* Appropriate tests (unit, integration etc)
  

##### A quick explanation of:
###### Your application, how you set it up, how it was built, how you designed the surge pricing and the type of architecture chosen.
* The application comprises 3 layers...
  * public and private HTTP controllers offering 3 endpoints, **see USAGE.md for details**
  * a service layer for the controllers that marshals data, calculates surge pricing and tracks the viewing of Items
  * a data layer that persists data, in-memory
  

* ...and an authentication service that restricts access to endpoints
  

* I chose this architecture (MVC/REST) almost as a reflex to the requirements, as it is a widely used pattern that most other devs will recognize and easily be able to support, modify and extend.

* The tech stack is: Java (11), SpringBoot (2.x)
  * I felt these technologies would be the most congenial option for a team already using SpringBoot.
  

* **Surge pricing** design:
  * I needed to persist the timestamps of recent *views* of inventory
    * I was sorely tempted at first to use an in-memory DB like H2 to store them but it seemed a bit overkill for the requirements. 
      * And, I wasn't entirely sure you wouldn't spank me for bending the guidelines a bit 8^)
    * ultimately I chose a thread-safe JDK Collection type as in-memory persistence
    * since the data access was so fast I simply *created/stored* the timestamp whenever the inventory was queried
  * I simply stream/filtered the *view timestamps* and determined the need for **Surge pricing** based upon the *size* of the result
  

* How I built it:
  * See the **authentication mechanism** section below for more details on how I got started
    * I felt the *authentication mechanism* was my greatest unknown so I started my research on how-to there...
###### Choice of *data format*. Include one example of a request and response.
* JSON serialization (w/Jackson)
  * See **USAGE.md** for example Request/Responses
  
###### What *authentication mechanism* was chosen, and why? [X]
* I had never used Spring Security before but:
  * I felt it would be the most acceptable option to a team already using Spring.
  * I briefly searched for the simplest possible mechanism to start with, recognising that it should be able to be made more robust later.
    * props to this blog post/author:
      * https://octoperf.com/blog/2018/03/08/securing-rest-api-spring-security/
      * https://github.com/jloisel/securing-rest-api-spring-security
  * I landed upon this approach (as the simplest thing that could possibly work/actually get done on time):
    * returning a UUID token in response to a GET to a public (i.e. unauthenticated) URL
    * requiring a (UUID) token in the Authorization HTTP Header of any private URLs.


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
* generate API docs from test w/Spring Docs? [ ]
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

