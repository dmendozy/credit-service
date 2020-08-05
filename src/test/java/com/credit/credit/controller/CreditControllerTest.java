package com.credit.credit.controller;

import com.credit.credit.model.Credit;
import com.credit.credit.repository.CreditRepository;
import com.credit.credit.service.CreditService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CreditController.class)
@Import(CreditService.class)
public class CreditControllerTest {
    @MockBean
    CreditRepository repository;


    @Autowired
    private WebTestClient webClient;

    final private static Map<String, Credit> creditMap = new HashMap<>();

    @BeforeAll
    public static void setup(){
        LocalDate creationDate = LocalDate.parse("2020-01-01");
        LocalDate expirationPayment = LocalDate.parse("2020-10-10");
        creditMap.put("test",new Credit("1","1","12312","1",1000.0,10.0,creationDate,expirationPayment,"1"));
    }

    @Test
    public void testCreateCredit(){
        Mockito
                .when(repository.save(creditMap.get("test"))).thenReturn(Mono.just(creditMap.get("test")));

        webClient.post()
                .uri("/credits")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(creditMap.get("test")))
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(repository,Mockito.times(1)).save(creditMap.get("test"));

    }


    @Test
    public void testGetCreditById(){
        Mockito
                .when(repository.findById(creditMap.get("test").creditId))
                .thenReturn(Mono.just(creditMap.get("test")));

        webClient.get()
                .uri("/credits/{id}",creditMap.get("test").creditId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Credit.class)
                .isEqualTo(creditMap.get("test"));
        Mockito.verify(repository, Mockito.times(1)).findById(creditMap.get("test").creditId);
    }

    @Test
    public void testUpdateCredit(){
        Mockito
                .when(repository.findById(creditMap.get("test").creditId))
                .thenReturn(Mono.just(creditMap.get("test")));

        webClient.put()
                .uri("/credits/{id}",creditMap.get("test").creditId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(creditMap.get("test")))
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(repository,Mockito.times(1)).save(creditMap.get("test"));
    }

    @Test
    public void testDeleteCredit(){
        Mockito
                .when(repository.deleteById(creditMap.get("test").creditId))
                .thenReturn(Mono.empty());

        webClient.delete()
                .uri("/credits/{id}",creditMap.get("test").creditId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Credit.class)
                .isEqualTo(null);
        Mockito.verify(repository, Mockito.times(1)).deleteById(creditMap.get("test").creditId);

    }
}
