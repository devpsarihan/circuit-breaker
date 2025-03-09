# Circuit Breaker App
### Project Goal
This app was developed to explain circuit breaker and its features.

### Tech Stack
* Resilience4J
* Java 21
* Spring Boot v3.4.3
* MongoDB
* Testcontainers
* Docker

### Run the Project
* Compile with Java 21
* Go to the project folder and run this commands
```
  $ cd circuit breaker
  $ mvn clean install 
  $ docker build -t circuit-breaker-image .
  $ docker-compose -f docker-compose.yaml up -d
```
* If you want to add host file
```
0.0.0.0 circuit-breaker
```

### Curl Commands
```
curl --location 'http://localhost:8080/v1/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "items": [
        {
            "contentId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
            "name": "Item1",
            "description": "Description1",
            "quantity": 1,
            "price": 100.0
        }
    ],
    "seller": {
        "id": "e19f9b1e-5f8d-4c6e-8007-ecf7a372a0e1",
        "sellerName": "Seller Name"
    },
    "customer": {
        "id": "7f8b8a1e-5f8d-4c6e-8007-ecf7a372a0e1",
        "firstName": "CustomerFirstName1",
        "lastName": "CustomerLastName1",
        "email": "customer1@example.com"
    },
    "invoiceAddress": "Invoice Address",
    "shippingAddress": "Shipping Address",
    "totalPrice": 100.0
}'
```
```
curl --location 'http://localhost:8080/v1/orders/{orderId}'
```
```
curl --location --request PUT 'http://localhost:8080/v1/orders/{orderId}' \
--header 'Content-Type: application/json' \
--data '{
    "status": 4
}'
```
