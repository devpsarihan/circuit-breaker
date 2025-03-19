package com.circuit_breaker.persistence.repository;

import com.circuit_breaker.persistence.document.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMongoRepository extends MongoRepository<Order, ObjectId> {

}
