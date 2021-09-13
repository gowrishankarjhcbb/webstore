##Webstore


## Requirements
1. Java 11
2. Maven 3.6.4 or greater


#### Build and run the app using maven

    mvn clean install
   
## Explore Rest APIs

The app defines following Endpoints.

  
    
     1. check bill - this is used for price calculation
     
     curl --location --request POST 'http://localhost:8080/checkbill' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "purchaseItems": [
            {
                "productId": 3,
                "quantity": 25
            }
        ]
    }'
    
    2. Create Product
    
     curl --location --request POST 'http://localhost:8080/api/products' \
     --header 'Content-Type: application/json' \
     --data-raw '{
         "name": "Penguin-ears",
         "pricePerCarton": 175.00,
         "noOfUnitsInCarton": 20
     }'
    

You can test them using postman or any other rest client.
