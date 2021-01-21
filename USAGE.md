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

### Trying out the API(s)

#### The following endpoints are available:
| Use Case | HTTP Verb | Endpoint |
| :------- | :-------- | :------: |
| **Register** with a valid email address | POST | /public/users/register |
| **Retrieve** a complete list of Items | GET | /private/inventory/list |
| **Buy** a quantity of an Item -- i.e. by Item ID; these IDs are exposed in the Inventory list | POST | /private/inventory/buy/#/ofItem/# |

#### Example Requests/Responses:
* Try the **secured** Inventory API **w/o authentication**:
    * curl http://localhost:8080/private/inventory/list
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
    * curl -H 'Authorization: Bearer asdf-qwerty-12345' http://localhost:8080/private/inventory/list
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
    * curl -X POST -d 'email=a.b@c.de' http://localhost:8080/public/users/register
        * returns the message:
          ```
          Sorry, that email address is not setup for shipping/billing - please visit https://gildedrose.com/accounts to set that up first.
          ```
                  
* Register for API use with a **recognized email**:
    * curl -X POST -d 'email=customer_x@go.to' http://localhost:8080/public/users/register
      * returns the message containing an *authentication token*: 
        ```
        Welcome customer_x@go.to, please use the following API token to shop at Gilded Rose ===>eaa12ff9-db63-405a-bbe9-4edec303c98b<===.
        ```
        
* GET the current Inventory w/valid authentication token:
    * curl -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/list
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
    * curl -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/1/ofItem/5
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
    * curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/321/ofItem/xxx
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
      
* Try to buy an Item, but using an **invalid** (i.e. non-numeric) quantity:
    * curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/asdf/ofItem/1
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
Similarly to the last case (above) the messaging here is a little opaque here due to the fact that a *UnderStockedItem* model is being re-used here 
(I'm too lazy/busy to improve it for now, there's a TODO in the source code).

Hopefully its enough to get a caller looking in the right direction...

* Try to buy an Item, but using an **invalid** Item identifier (i.e. numeric, but not available from inventory):
    * curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/321/ofItem/666
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
    * curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/4096/ofItem/5
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
    * curl -X POST -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/1/ofItem/5
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
    * curl -H 'Authorization: Bearer eaa12ff9-db63-405a-bbe9-4edec303c98b' http://localhost:8080/private/inventory/buy/2/ofItem/1
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

Pellentesque id sapien laoreet, dapibus lacus eget, elementum dolor. Phasellus vitae tortor sit amet justo sollicitudin elementum. Donec porta lectus vitae velit imperdiet aliquet. Cras maximus commodo ex. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam pharetra dolor ac ex luctus placerat. In orci est, venenatis at neque vel, vehicula eleifend massa. Praesent condimentum nibh eu ultrices luctus. Sed mi enim, efficitur quis magna sed, dignissim dignissim nunc. Mauris quis rutrum dolor. Maecenas quis laoreet tellus, ut hendrerit arcu.

In hac habitasse platea dictumst. Duis id eleifend arcu. Quisque congue elit augue, a accumsan arcu viverra quis. Suspendisse eu purus aliquet, feugiat nulla quis, tempor elit. Curabitur commodo ex elit, ac convallis felis suscipit ac. Aliquam nec efficitur libero, vitae elementum orci. Proin velit lorem, pellentesque id nibh ultrices, semper finibus magna. Etiam sollicitudin venenatis odio, sit amet lacinia massa malesuada ut. Nam iaculis sagittis enim nec convallis.

Etiam fringilla vestibulum fermentum. Mauris suscipit enim in condimentum sodales. Vestibulum accumsan neque ut pulvinar fringilla. Donec ut eros vehicula, condimentum mauris in, dapibus erat. Integer eget feugiat eros. Nullam at pellentesque erat. Nullam et orci accumsan, sollicitudin lacus in, volutpat nibh. Nullam fringilla placerat elit vel scelerisque. Vestibulum non nisl sit amet dolor porta suscipit id nec nunc. Maecenas auctor, est sagittis fringilla venenatis, magna nunc vulputate erat, in blandit lorem felis vulputate nisi. Duis eget purus sit amet nisi posuere pretium. Ut viverra velit volutpat, sodales ligula et, facilisis tellus. Pellentesque hendrerit iaculis eros sed mollis. Donec odio lacus, dictum ac finibus condimentum, bibendum et eros.

Suspendisse commodo orci vel lacus dapibus, in venenatis justo laoreet. Aliquam rutrum interdum felis quis lobortis. Cras id turpis enim. Aliquam a lacinia ante. Fusce vitae nibh felis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nam facilisis, sem in sodales finibus, velit arcu tempus leo, eget lobortis velit erat quis lorem. Phasellus id mauris massa.

Curabitur commodo enim nulla, a lobortis ligula tincidunt nec. Sed sed facilisis metus. Nullam interdum nisl tortor, id consequat eros porta ut. Mauris rutrum, tortor in condimentum feugiat, leo augue iaculis lorem, eu volutpat lorem enim eu dui. Vivamus vulputate velit eu consectetur facilisis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed ut odio ut massa cursus finibus sed ac ipsum. Suspendisse vehicula libero vel ex pharetra, a eleifend neque finibus.

Maecenas vitae facilisis magna, id lobortis lorem. Sed ipsum nibh, rhoncus vel gravida tempor, euismod sit amet dolor. Duis placerat massa at metus bibendum, eget placerat elit semper. Cras ac odio velit. Pellentesque id porta ex. Curabitur pulvinar tellus a gravida mattis. Aliquam tristique nisl aliquet condimentum feugiat. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.

Donec commodo malesuada purus luctus condimentum. Donec fringilla blandit magna in luctus. Proin volutpat felis eros, ut aliquam elit consectetur ut. Etiam nec lobortis turpis. Cras congue leo et erat dapibus, a porttitor lectus lobortis. Aliquam erat volutpat. Nam mollis pulvinar risus nec lacinia. Vestibulum volutpat, lectus vel ornare porttitor, nisl sapien malesuada massa, ac feugiat nibh sem vitae justo. In magna mauris, eleifend eu orci quis, hendrerit posuere lacus. Morbi enim erat, convallis in interdum et, malesuada nec orci. Vivamus gravida lectus urna, et luctus lorem ultrices eget. Nullam gravida lorem in leo vehicula venenatis. Sed lacinia euismod arcu, eget tincidunt tortor mattis sit amet. Maecenas vitae sollicitudin neque.

Vestibulum viverra, metus vel lacinia lobortis, tortor mauris malesuada enim, in rutrum ex risus vel mauris. Proin et rhoncus ex. Nullam nec ex at est facilisis auctor eu vel dolor. Ut sit amet velit ac magna maximus maximus. Proin a sapien lorem. Nulla facilisis venenatis leo, quis sagittis tellus ullamcorper id. Nam venenatis risus quis enim auctor, et malesuada lacus interdum. Nunc interdum felis ac varius malesuada. Aliquam et tincidunt turpis. Suspendisse interdum metus in nulla finibus bibendum in at eros.

Quisque est diam, tempor ac erat vitae, rutrum sagittis tortor. Curabitur vel lacinia lorem. Maecenas dignissim posuere ante eget ullamcorper. Aliquam tempus accumsan hendrerit. Phasellus aliquam arcu nec justo cursus bibendum. Nunc ultrices vitae lorem id pellentesque. Sed pretium mollis sapien, ut laoreet lacus congue nec. Suspendisse accumsan in ex a varius. Sed elit enim, suscipit in metus a, tristique iaculis felis. Ut eu metus in diam ullamcorper aliquam. Pellentesque ac purus et enim gravida vestibulum. Morbi varius risus ante, vel elementum odio varius at. Cras lobortis justo at tortor sollicitudin sodales at eget nisl. Sed cursus mauris sagittis est interdum posuere. Ut et nulla eu est venenatis bibendum eu nec urna. Praesent et ligula vestibulum, lobortis orci sit amet, auctor sem.

Mauris ut justo a velit sodales feugiat. Proin facilisis at neque eget rhoncus. Nulla urna enim, fringilla at tellus vel, congue aliquet orci. Integer quis tellus a ipsum gravida elementum nec eu nulla. Aliquam massa dolor, lacinia a bibendum ut, tincidunt tempus est. Nunc mattis placerat ornare. Aliquam tempor fringilla dui, vitae volutpat justo faucibus quis. Ut bibendum ac felis eget rhoncus. Duis egestas enim vel massa vestibulum luctus. Vestibulum at efficitur justo. Nam cursus varius neque a tristique. Etiam eu ipsum vitae risus vehicula mollis. Vestibulum in dictum dui. Curabitur vel fringilla orci. Sed sed vehicula arcu, nec accumsan erat. Phasellus varius semper turpis et semper.

Donec iaculis rutrum dignissim. Pellentesque viverra velit sem. Duis convallis pulvinar nisl quis sollicitudin. Nullam eleifend, nisl nec elementum faucibus, nisi turpis consectetur lorem, nec rutrum velit elit at enim. Curabitur ligula turpis, sollicitudin ac lorem non, malesuada rhoncus lacus. Suspendisse potenti. Integer sapien eros, imperdiet non hendrerit quis, auctor eget justo. Ut neque quam, commodo non vestibulum at, tempus at magna. Pellentesque ac purus in quam convallis suscipit. Proin vel nulla ipsum. Suspendisse auctor tristique arcu eu congue. Nam et rutrum dui. Ut vel leo lorem.

Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Quisque aliquam ligula a est tempor eleifend. Integer faucibus fermentum ornare. Donec sagittis risus ac commodo accumsan. Sed rhoncus pellentesque nibh quis malesuada. Integer rhoncus quam in ex molestie, ut faucibus nibh sagittis. Praesent lacinia ex et tristique mollis. In ac diam diam. Sed sagittis lectus et posuere sodales.
