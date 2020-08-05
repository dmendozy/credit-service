package com.credit.credit.service;

import com.credit.credit.model.Credit;
import com.credit.credit.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditService {
    @Autowired
    private CreditRepository creditRepository;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    public Flux<Credit> getAll() {
        return creditRepository.findAll();
    }

    public Mono<Credit> getById(String id) {
        return creditRepository.findById(id);
    }

    public Mono update(String id, Credit credit) {
        return creditRepository.save(credit);
    }

    public Mono save(Credit credit) {
        return creditRepository.save(credit);
    }

    public Mono delete(String id) {
        return creditRepository.deleteById(id);
    }

    public Flux<Credit> getByCustomerId(String customerId) {
        return creditRepository.findByCustomerId(customerId);
    }

}
