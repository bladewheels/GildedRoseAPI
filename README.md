# GildedRoseAPI

## This is a Java/SpringBoot-based HTTP/REST API server for a fictitious entity, 'The Gilded Rose'.

* The requirements are: "...to produce a public HTTP-accessible API. The Gilded Rose would like to utilize a **surge pricing** model like Uber to increase the price of *items* by 10% if they have been **viewed** *more than 10 times in an hour*."
   * i.e.:
     * Retrieve the current inventory (i.e. list of items) [ ]
     * Buy an item (user must be authenticated) [ ]
     * Here is the definition for an item:
         ```C#
         class Item 
         { 
            public    String    Name {get; set;} 
            public    String    Description {get; set;} 
            public    int       Price {get; set;} 
         }
         ```

* A couple of questions to consider: 
  * How do we know if a user is *authenticated*? 
  * Is it *always possible* to buy an item? 

* Please model (and test!) accordingly. Using **a real database is not required**. 
* Deliverables:
  * A runnable system with instructions on how to build/run your application [ ]
  * The application should be built using Java and the Spring framework [ ]
  * The application should be able to be run from the command line without any dependencies or database’s required to run your application.  (Other than maven, gradle and Java) [ ]
  * A system that can process the two API requests via HTTP [ ]
  * Appropriate tests (unit, integration etc) [ ]
  * A quick explanation of: 
     * Your application, how you set it up, how it was built, how you designed the surge pricing and the type of architecture chosen. [ ]
     * Choice of *data format*. Include one example of a request and response. [ ]
     * What *authentication mechanism* was chosen, and why? [ ]

## Points to ponder:
* use HATEOS? [ ]
* use JSON schemas? [ ]
* introduce & expose Item IDs? [ ]
* /list endpoint:
  * filter w/query params? [ ]
  * ranges e.g. names starting with? IDs? [ ]
  * pagination? [ ]
    * (variable?) page size? [ ]
    * how to determine serverside *viewed* status, for each item, for the purposes of determing *surge pricing*?
  * consciously choose to return *in-stock* yes/no attribute with each item [ ]
    * i.e. rather than *not* returning items that *are* out-of-stock
    * this allows callers to parallelize their *get-all, by-page* calls *without* inadvertently missing/duplicating items that go in or out of stock during the query time
* /buy endpoint:
  * quantity defaults to: 1
  * return 400 if quantity > in-stock count?
  * how to safely (i.e. ACID) handle concurrent decrements to stock level?
  * does surge pring apply if fulfilling *part* of executing a buy request tips the items/hour *over* the threshold?
* *surge pricing*:
  * applies to 10 times in current *wallclock* hour or *duration* hour?
    * likely *last 60 minutes* is implied in **surge** nomenclature
  * concurrent listings of items need to be aggregated & processed by surge pricer
    * data structure for surge pricer process?
      * ring buffer or dequeue or ...?
      * needs rolling time window to determine surge
      * simplest: DB View like: COUNT(views) WHERE viewedAt >= (NOW() - 1 hour) GROUP BY ItemID
        * needs cleanup script to cull old views, @Scheduled?
* generate API docs from test w/Spring Docs? [ ]
* use H2 DB and persist to local file? [ ]
  * Or, just use HashMap as DB?
    * key on Item ID

