# Trying out the API(s)
* Try the Inventory API w/o authentication:
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
        
* Register w/email:
    * curl -XPOST -d 'email=a.b@c.de' http://localhost:8080/public/users/register
      * returns the token: 
        ```
        eaa12ff9-db63-405a-bbe9-4edec303c98b
        ```
        
* Try the Inventory API w/authentication token:
    * curl -H 'Authorization: Bearer 75db1cc6-ec0a-4426-b66d-5dc587d66259' http://localhost:8080/private/inventory/list
        * returns the JSON:
            ``` JSON
              {
                "id":"75db1cc6-ec0a-4426-b66d-5dc587d66259",
                "username":"john",
                "enabled":true
              }
            ```

* Logout:
    * curl -H 'Authorization: Bearer 75db1cc6-ec0a-4426-b66d-5dc587d66259' http://localhost:8080/private/users/logout
        * returns: true
    
* Try the Inventory API w/o authentication:
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
                    