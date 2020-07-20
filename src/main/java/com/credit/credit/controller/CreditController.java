package com.credit.credit.controller;

import com.credit.credit.model.Credit;
import com.credit.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditController {
    @Autowired
    CreditService creditService;

    @GetMapping
    public Flux<Credit> getAllCredits(){
        return creditService.getAll();
    }
    @GetMapping("{id}")

    public Mono<Credit> getCreditById(@PathVariable("id") String accountId){
        return creditService.getById(accountId);
    }

    @PostMapping
    public Mono<Credit> createCredit(@Validated @RequestBody Credit credit){
        return creditService.save(credit);
    }

    @PutMapping("{id}")
    public Mono<Credit> updateCredit(@PathVariable("id") String creditId,
                                       @Validated @RequestBody Credit credit){
        return creditService.update(creditId, credit);
    }

    @DeleteMapping("{id}")
    public Mono<Credit> deleteCredit(@PathVariable("id") String creditId){
        return creditService.delete(creditId);
    }

}
