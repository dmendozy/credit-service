package com.credit.credit.service;

import com.credit.credit.model.Credit;
import com.credit.credit.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditService {
    @Autowired
    private CreditRepository creditRepository;

    public Flux<Credit> getAll(){
        return creditRepository.findAll();
    }
    public Mono<Credit> getById(String id){
        return creditRepository.findById(id);
    }
    public Mono update(String id, Credit credit){
        return creditRepository.save(credit);
    }
    public Mono save(Credit credit){
        return creditRepository.save(credit);
    }
    public Mono delete(String id){
        return creditRepository.deleteById(id);
    }
    public Flux<Credit> getByCustomerId(String customerId){return creditRepository.findByCustomerId(customerId);}

}
