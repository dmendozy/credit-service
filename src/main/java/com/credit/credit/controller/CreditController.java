package com.credit.credit.controller;

import com.credit.credit.model.Credit;
import com.credit.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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

    @GetMapping("/customer/{customerId}")
    public Flux<Credit> findByCustomer(@PathVariable("customerId") String customerId) {
        return creditService.getByCustomerId(customerId);
    }

    @GetMapping("/search/{bankId}/{firstDate}/{lastDate}")
    public Flux<Credit> getAccountsBetweenDates(@PathVariable("bankId") String bankId,
                                                 @PathVariable("firstDate") String firstDate,
                                                 @PathVariable("lastDate") String lastDate) {
        LocalDate date1 = LocalDate.parse(firstDate);
        LocalDate date2 = LocalDate.parse(lastDate);
        return creditService.getAll()
                .filter(credit -> credit.getBankId()!=null&&
                        credit.getCreationDate().compareTo(date1)>=0&&
                        credit.getCreationDate().compareTo(date2)<=0)
                .flatMap(account -> {
                    return creditService.getById(account.getCreditId());
                });
    }

}
