# GildedRoseAPI

## This is a Java/SpringBoot-based HTTP/REST API server for a fictitious entity, 'The Gilded Rose'.

1. The requirements are: to produce a public HTTP-accessible API. The Gilded Rose would like to utilize a **surge pricing** model like Uber to increase the price of *items* by 10% if they have been viewed *more than 10 times in an hour*.
   1. i.e.:
      1. [ ] Retrieve the current inventory (i.e. list of items)
      2. [ ] Buy an item (user must be authenticated) 
      3. Here is the definition for an item:
```C#
class Item 
{ 
public    String    Name {get; set;} 
public    String    Description {get; set;} 
public    int    Price {get; set;} 
}
```

1. A couple of questions to consider: 
   1. How do we know if a user is *authenticated*? 
   1. Is it *always possible* to buy an item? 

1. Please model (and test!) accordingly. Using **a real database is not required**. 
1. Deliverables:
   1. [ ] A runnable system with instructions on how to build/run your application
   1. [ ] The application should be built using Java and the Spring framework
   1. [ ] The application should be able to be run from the command line without any dependencies or database’s required to run your application.  (Other than maven, gradle and Java)
   1. [ ] A system that can process the two API requests via HTTP 
   1. [ ] Appropriate tests (unit, integration etc) 
   1. A quick explanation of: 
      1. [ ] Your application, how you set it up, how it was built, how you designed the surge pricing and the type of architecture chosen.
      1. [ ] Choice of *data format*. Include one example of a request and response. 
      1. [ ] What *authentication mechanism* was chosen, and why?

