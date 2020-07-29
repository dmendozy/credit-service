package com.credit.credit.repository;

import com.credit.credit.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {
    Flux<Credit> findByCustomerId(String customerId);

}
