package com.credit.credit.controller;

import com.credit.credit.adds.Bank;
import com.credit.credit.adds.Transaction;
import com.credit.credit.model.Credit;
import com.credit.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/credits")
public class CreditController {
    @Autowired
    CreditService creditService;

    @Autowired
    WebClient.Builder webClientBuilder;

    @GetMapping
    public Flux<Credit> getAllCredits() {
        return creditService.getAll();
    }

    @GetMapping("{id}")
    public Mono<Credit> getCreditById(@PathVariable("id") String accountId) {
        return creditService.getById(accountId);
    }

    @PostMapping
    public Mono createCredit(@Validated @RequestBody Credit credit) {
        Mono bank = webClientBuilder
                .build()
                .put()
                .uri("http://localhost:8080/banks/" + credit.getBankId() + "/" + credit.getCustomerId())
                .retrieve()
                .bodyToMono(Bank.class);
        return bank.flatMap(b -> {
            return creditService.save(credit);
        });
    }

    @PutMapping("{id}")
    public Mono<Credit> updateCredit(@PathVariable("id") String creditId,
                                     @Validated @RequestBody Credit credit) {
        return creditService.update(creditId, credit);
    }

    @DeleteMapping("{id}")
    public Mono<Credit> deleteCredit(@PathVariable("id") String creditId) {
        return creditService.delete(creditId);
    }

    @GetMapping("/customer/{customerId}")
    public Flux<Credit> findByCustomer(@PathVariable("customerId") String customerId) {
        return creditService.getByCustomerId(customerId);
    }

    //Search credits between dates
    @GetMapping("/search/{bankId}/{firstDate}/{lastDate}")
    public Flux<Credit> getCreditsBetweenDates(@PathVariable("bankId") String bankId,
                                               @PathVariable("firstDate") String firstDate,
                                               @PathVariable("lastDate") String lastDate) {
        LocalDate date1 = LocalDate.parse(firstDate);
        LocalDate date2 = LocalDate.parse(lastDate);
        return creditService.getAll()
                .filter(credit -> credit.getBankId().equals(bankId) &&
                        credit.getCreationDate().compareTo(date1) >= 0 &&
                        credit.getCreationDate().compareTo(date2) <= 0)
                .flatMap(account -> {
                    return creditService.getById(account.getCreditId());
                });
    }

    //Search credits by expiration date
    @GetMapping("/expiration/{customerId}")
    public Mono getCreditBlocked(@PathVariable("customerId") String id) {
        return creditService.getByCustomerId(id)
                .filter(credit -> credit.getExpirationPayment().compareTo(LocalDate.now()) < 0)
                .collectList();
    }

    //Pay credit
    @PutMapping("/pay/{creditId}/{amount}")
    public Mono payCredit(@PathVariable("creditId") String creditId,
                          @PathVariable("amount") double amount) {
        return creditService.getById(creditId)
                .flatMap(credit -> {
                    if (credit.getCreditConsumed() > 0) {
                        credit.setCreditAvailable(credit.getCreditAvailable() + amount);
                        credit.setCreditConsumed(credit.getCreditConsumed() - amount);
                    }
                    return creditService.save(credit);
                });
    }

    //Add credit consumed
    @PutMapping("/charge/{creditId}/{amount}")
    public Mono addCreditConsumed(@PathVariable("creditId") String creditId,
                                  @PathVariable("amount") double amount) {
        return creditService.getById(creditId)
                .flatMap(credit -> {
                    if (credit.getCreditAvailable() > 0 && credit.getCreditAvailable() >= amount &&
                            credit.getCreditConsumed() >= 0) {
                        credit.setCreditConsumed(credit.getCreditConsumed() + amount);
                        credit.setCreditAvailable(credit.getCreditAvailable() - amount);
                    }
                    Transaction transaction = new Transaction("Add credit consumption", amount, LocalDateTime.now(), creditId);
                    Mono<Transaction> transactionMono = webClientBuilder
                            .build()
                            .post()
                            .uri("http://localhost:8080/transactions/")
                            .body(Mono.just(transaction), Transaction.class)
                            .retrieve()
                            .bodyToMono(Transaction.class);
                    return transactionMono.flatMap(t -> {
                        return creditService.save(credit);
                    });
                });
    }

}
