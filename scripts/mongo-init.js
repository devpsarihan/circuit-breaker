db = connect("mongodb://mongodb:27017/circuit-breaker?replicaSet=rs0");
db.getCollection('orders').drop();
db.createCollection("orders");

db.getCollection('orders').insertMany([
  {
    "items": [
      {
        "contentId": "d290f1ee-6c54-4b01-90e6-d701748f0851",
        "name": "Item1",
        "description": "Description1",
        "quantity": 1,
        "price": 10.00
      },
      {
        "contentId": "1b290f1e-6c54-4b01-90e6-d701748f0852",
        "name": "Item2",
        "description": "Description2",
        "quantity": 2,
        "price": 20.00
      }
    ],
    "seller": {
      "id": "c290f1ee-6c54-4b01-90e6-d701748f0853",
      "sellerName": "Seller1"
    },
    "customer": {
      "id": "a290f1ee-6c54-4b01-90e6-d701748f0854",
      "firstName": "CustomerFirstName1",
      "lastName": "CustomerLastName1",
      "email": "customer1@example.com"
    },
    "invoiceAddress": "Invoice Address",
    "shippingAddress": "Shipping Address",
    "status": 0,
    "totalPrice": 50.00,
    "createdDate": new Date("2025-03-05T10:32:58Z"),
    "modifiedDate": new Date("2025-03-05T10:32:58Z")
  },
  {
    "items": [
      {
        "contentId": "e290f1ee-6c54-4b01-90e6-d701748f0851",
        "name": "Item3",
        "description": "Description3",
        "quantity": 3,
        "price": 30.00
      },
      {
        "contentId": "2b290f1e-6c54-4b01-90e6-d701748f0852",
        "name": "Item4",
        "description": "Description4",
        "quantity": 4,
        "price": 40.00
      }
    ],
    "seller": {
      "id": "d290f1ee-6c54-4b01-90e6-d701748f0853",
      "sellerName": "Seller2"
    },
    "customer": {
      "id": "b290f1ee-6c54-4b01-90e6-d701748f0854",
      "firstName": "CustomerFirstName2",
      "lastName": "CustomerLastName2",
      "email": "customer2@example.com"
    },
    "invoiceAddress": "Invoice Address",
    "shippingAddress": "Shipping Address",
    "status": 1,
    "totalPrice": 100.00,
    "createdDate": new Date("2025-03-05T10:32:58Z"),
    "modifiedDate": new Date("2025-03-05T10:32:58Z")
  }
]);