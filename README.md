# GildedRoseAPI

## This is a Java/SpringBoot-based HTTP/REST API server for a fictitious entity, 'The Gilded Rose'.

### The requirements are thus:

#### ...a public HTTP-accessible API. The Gilded Rose would like to utilize a surge pricing model like Uber to increase the price of items by 10% if they have been viewed more than 10 times in an hour.

##### API requirements 
###### Retrieve the current inventory (i.e. list of items)
###### Buy an item (user must be authenticated) 

###### Here is the definition for an item: 
class Item 
{ 
public    String    Name {get; set;} 
public    String    Description {get; set;} 
public    int    Price {get; set;} 
} 

###### A couple of questions to consider: 
####### How do we know if a user is authenticated? 
####### Is it always possible to buy an item? 

###### Please model (and test!) accordingly. Using a real database is not required. 
###### Deliverables 
####### A runnable system with instructions on how to build/run your application
####### The application should be built using Java and the Spring framework
####### The application should be able to be run from the command line without any dependencies or database’s required to run your application.  (Other than maven, gradle and Java)
####### A system that can process the two API requests via HTTP 
####### Appropriate tests (unit, integration etc) 
####### A quick explanation of: 
######## Your application, how you set it up, how it was built, how you designed the surge pricing and the type of architecture chosen.
######## Choice of data format. Include one example of a request and response. 
######## What authentication mechanism was chosen, and why?

